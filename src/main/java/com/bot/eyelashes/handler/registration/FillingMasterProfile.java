package com.bot.eyelashes.handler.registration;

import com.bot.eyelashes.cache.MasterDataCache;
import com.bot.eyelashes.enums.BotState;
import com.bot.eyelashes.enums.ClientBotState;
import com.bot.eyelashes.handler.impl.HandleActivityMaster;
import com.bot.eyelashes.handler.impl.HandleTypeOfActivityImpl;
import com.bot.eyelashes.model.dto.MasterDto;
import com.bot.eyelashes.service.MessageService;
import com.bot.eyelashes.validation.PhoneNumberValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class FillingMasterProfile implements HandleRegistration {
    private final MasterDataCache masterDataCache;

    private final MessageService messageService;

    @Override
    public SendMessage getMessage(Message message) {
        if (masterDataCache.getUsersCurrentBotState(message.getChatId()).equals(BotState.FILLING_PROFILE)) {
            masterDataCache.setUsersCurrentBotState(message.getFrom()
                    .getId(), BotState.ASK_NAME);
        }
        return processUsersInput(message);
    }

    @Override
    public BotState getHandleName() {
        return BotState.FILLING_PROFILE;
    }

    private SendMessage processUsersInput(Message inputMsg) {
        String usersAnswer = inputMsg.getText();
        Long chatId = inputMsg.getChatId();

        MasterDto masterDto = masterDataCache.getUserProfileData(chatId);
        BotState botState = masterDataCache.getUsersCurrentBotState(chatId);

        SendMessage replyToUser = null;
        if (botState.equals(BotState.ASK_NAME)) {
            replyToUser = messageService.getReplyMessage(chatId, "Введите Ваше имя");
            masterDataCache.setUsersCurrentBotState(chatId, BotState.ASK_SURNAME);
        }if(botState.equals(BotState.ASK_SURNAME)){
            masterDto.setName(usersAnswer);
            replyToUser = messageService.getReplyMessage(chatId, "Введите Вашу фамилию");
            masterDataCache.setUsersCurrentBotState(chatId, BotState.ASK_PHONE);
        }
        if (botState.equals(BotState.ASK_PHONE)) {
            masterDto.setSurname(usersAnswer);
            replyToUser = messageService.getReplyMessage(chatId, "Введите номер телефона начиная с +7: ");
            masterDataCache.setUsersCurrentBotState(chatId, BotState.ASK_ACTIVITY);
        }
        if (botState.equals(BotState.ASK_ACTIVITY)) {
            if (PhoneNumberValidation.isValidPhone(usersAnswer)) {
                masterDto.setPhone(usersAnswer);
                masterDto.setTelegramId(chatId);
                HandleActivityMaster handleTypeOfActivity = new HandleActivityMaster();
                masterDataCache.setUsersCurrentBotState(chatId, BotState.PROFILE_FIELD);
                replyToUser = SendMessage.builder()
                        .text("Выберите вид деятельности")
                        .replyMarkup(handleTypeOfActivity.createInlineKeyboard())
                        .chatId(chatId.toString())
                        .build();
            } else
                replyToUser = messageService.getReplyMessage(chatId, "Некорректный номер телефона");

        }
        if (botState.equals(BotState.PROFILE_FIELD)) {
            masterDto.setActivity(usersAnswer);
            masterDataCache.setMasterInDb(masterDto);
            replyToUser = SendMessage.builder()
                    .text("Запишите график работы")
                    .replyMarkup(keyboardForRegistration())
                    .chatId(chatId.toString())
                    .build();
        }
        if (botState.equals(BotState.REGISTREDET)) {
            masterDataCache.setMasterInDb(masterDto);
            replyToUser = SendMessage.builder()
                    .text("Запишите график работы")
                    .replyMarkup(keyboardForRegistration())
                    .chatId(chatId.toString())
                    .build();
        }


        masterDataCache.saveUserProfileData(chatId, masterDto);
        return replyToUser;
    }

    private InlineKeyboardMarkup keyboardForRegistration() {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(Arrays.asList(
                InlineKeyboardButton.builder()
                        .callbackData("SCHEDULE")
                        .text("Расписание")
                        .build()
        ));
        return InlineKeyboardMarkup.builder()
                .keyboard(buttons)
                .build();
    }

    @Override
    public ClientBotState getHandleClientName() {
        return null;
    }
}
