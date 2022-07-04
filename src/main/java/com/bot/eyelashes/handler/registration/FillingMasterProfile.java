package com.bot.eyelashes.handler.registration;

import com.bot.eyelashes.cache.MasterDataCache;
import com.bot.eyelashes.enums.BotState;
import com.bot.eyelashes.enums.ClientBotState;
import com.bot.eyelashes.model.dto.MasterDto;
import com.bot.eyelashes.service.MessageService;
import com.bot.eyelashes.validation.PhoneNumberValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Slf4j
@RequiredArgsConstructor
public class FillingMasterProfile implements HandleRegistration {
    private final MasterDataCache masterDataCache;
    private final MessageService messageService;

    @Override
    public SendMessage getMessage(Message message) {
        if (masterDataCache.getUsersCurrentBotState(message.getFrom().getId()).equals(BotState.FILLING_PROFILE)) {
            masterDataCache.setUsersCurrentBotState(message.getFrom()
                    .getId(), BotState.ASK_FULL_NAME);
        }
        return processUsersInput(message);
    }

    @Override
    public BotState getHandleName() {
        return BotState.FILLING_PROFILE;
    }

    private SendMessage processUsersInput(Message inputMsg) {
        String usersAnswer = inputMsg.getText();
        Long userId = inputMsg.getFrom()
                .getId();
        Long chatId = inputMsg.getChatId();

        MasterDto masterDto = masterDataCache.getUserProfileData(userId);
        BotState botState = masterDataCache.getUsersCurrentBotState(userId);

        SendMessage replyToUser = null;
        if (botState.equals(BotState.ASK_FULL_NAME)) {
            replyToUser = messageService.getReplyMessage(chatId, "Введите ФИО");
            masterDataCache.setUsersCurrentBotState(userId, BotState.ASK_PHONE);
        }
        if (botState.equals(BotState.ASK_PHONE)) {
            String[] fullName = usersAnswer.split(" ");
            masterDto.setName(fullName[1]);
            masterDto.setSurname(fullName[0]);
            masterDto.setMiddleName(fullName[2]);
            replyToUser = messageService.getReplyMessage(chatId, "Введите номер телефона начиная с +7: ");
            masterDataCache.setUsersCurrentBotState(userId, BotState.ASK_ACTIVITY);
        }
        if (botState.equals(BotState.ASK_ACTIVITY)) {
            if (PhoneNumberValidation.isValidPhone(usersAnswer)) {
                masterDto.setPhone(usersAnswer);
                masterDto.setTelegramId(userId);
                replyToUser = messageService.getReplyMessage(chatId, "Введите услуги: ");
                masterDataCache.setUsersCurrentBotState(userId, BotState.PROFILE_FIELD);
            } else
                replyToUser = messageService.getReplyMessage(chatId, "Некорректный номер телефона");

        }
        if (botState.equals(BotState.PROFILE_FIELD)) {
            masterDto.setActivity(usersAnswer);
            masterDataCache.setMasterInDb(masterDto);
            replyToUser = SendMessage.builder()
                    .text("Запишите график работы")
                    .replyMarkup(keyboardForRegistration())
                    .chatId(userId.toString())
                    .build();
        }
        if (botState.equals(BotState.REGISTREDET)) {
            masterDataCache.setMasterInDb(masterDto);
            replyToUser = SendMessage.builder()
                    .text("Запишите график работы")
                    .replyMarkup(keyboardForRegistration())
                    .chatId(userId.toString())
                    .build();
        }


        masterDataCache.saveUserProfileData(userId, masterDto);
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
