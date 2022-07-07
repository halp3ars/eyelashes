package com.bot.eyelashes.handler.registration;

import com.bot.eyelashes.cache.MasterDataCache;
import com.bot.eyelashes.enums.BotState;
import com.bot.eyelashes.enums.ClientBotState;
import com.bot.eyelashes.handler.impl.*;
import com.bot.eyelashes.model.dto.MasterDto;
import com.bot.eyelashes.model.dto.ScheduleDto;
import com.bot.eyelashes.service.Bot;
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
        ScheduleDto userScheduleData = masterDataCache.getUserScheduleData(inputMsg.getChatId());
        SendMessage replyToUser = null;
        if (botState.equals(BotState.ASK_NAME)) {
            replyToUser = messageService.getReplyMessage(chatId, "Введите Ваше имя");
            masterDataCache.setUsersCurrentBotState(chatId, BotState.ASK_SURNAME);
        }
        if (botState.equals(BotState.ASK_SURNAME)) {
            masterDto.setName(usersAnswer);
            replyToUser = messageService.getReplyMessage(chatId, "Введите Вашу фамилию");
            masterDataCache.setUsersCurrentBotState(chatId, BotState.ASK_ADDRESS);
        }
        if (botState.equals(BotState.ASK_ADDRESS)) {
            masterDto.setTelegramNick(inputMsg.getFrom().getUserName());
            masterDto.setSurname(usersAnswer);
            replyToUser = messageService.getReplyMessage(chatId, "Введите адрес");
            masterDataCache.setUsersCurrentBotState(chatId, BotState.ASK_PHONE);
        }
        if (botState.equals(BotState.ASK_PHONE)) {
            masterDto.setAddress(usersAnswer);
            replyToUser = messageService.getReplyMessage(chatId, "Введите номер телефона начиная с +7: ");
            masterDataCache.setUsersCurrentBotState(chatId, BotState.ASK_ACTIVITY);
        }
        if (botState.equals(BotState.ASK_ACTIVITY)) {
            if (PhoneNumberValidation.isValidPhone(usersAnswer)) {
                masterDto.setPhone(usersAnswer);
                masterDto.setTelegramId(chatId);
                masterDataCache.setUsersCurrentBotState(chatId, BotState.ASK_DATE);
                HandleMasterActivityImpl handleTypeOfActivity = new HandleMasterActivityImpl();
                replyToUser = SendMessage.builder()
                        .text("Выберите вид деятельности")
                        .replyMarkup(handleTypeOfActivity.createInlineKeyboard())
                        .chatId(chatId.toString())
                        .build();
            } else
                replyToUser = messageService.getReplyMessage(chatId, "Некорректный номер телефона");

        }
        if(botState.equals(BotState.ASK_DATE)){
            masterDataCache.setUsersCurrentBotState(chatId, BotState.ASK_TIME_FROM);
            HandleMasterScheduleImpl handleMasterSchedule = new HandleMasterScheduleImpl(masterDataCache);
            replyToUser = SendMessage.builder()
                    .text("Выберите дни")
                    .replyMarkup(handleMasterSchedule.createInlineKeyboard())
                    .chatId(chatId.toString())
                    .build();
        }
        if (botState.equals(BotState.ASK_TIME_FROM)) {
            masterDataCache.setUsersCurrentBotState(chatId, BotState.ASK_TIME_TO);
            HandleMasterTimeFromImpl handleMasterTimeFrom = new HandleMasterTimeFromImpl();
            replyToUser = SendMessage.builder()
                    .text("Выберите время с")
                    .replyMarkup(handleMasterTimeFrom.createInlineKeyboard())
                    .chatId(chatId.toString())
                    .build();
        }
        if (botState.equals(BotState.ASK_TIME_TO)) {
            masterDataCache.setUsersCurrentBotState(chatId, BotState.REGISTERED);
            HandleMasterTimeToImpl handleMasterTimeTo = new HandleMasterTimeToImpl(masterDataCache);
            replyToUser = SendMessage.builder()
                    .text("Выберите время по")
                    .replyMarkup(handleMasterTimeTo.createInlineKeyboard())
                    .chatId(chatId.toString())
                    .build();
        }
        if (botState.equals(BotState.REGISTERED)) {
            masterDataCache.setMasterInDb(masterDto);
            userScheduleData.setTelegramId(inputMsg.getChatId());
            replyToUser = SendMessage.builder()
                    .text("Запишите график работы")
                    .chatId(chatId.toString())
                    .replyMarkup(createFinalButton())
                    .build();
            Bot.masterRegistration = false;
            masterDataCache.setScheduleInDb(userScheduleData);
            masterDataCache.setUsersCurrentBotState(chatId, BotState.NONE);
        }

        masterDataCache.saveUserProfileData(chatId, masterDto);
        return replyToUser;

    }

    private InlineKeyboardMarkup createFinalButton(){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(List.of(InlineKeyboardButton.builder().text("Меню").callbackData("MENU").build()));
        inlineKeyboardMarkup.setKeyboard(buttons);
        return inlineKeyboardMarkup;
    }

    @Override
    public ClientBotState getHandleClientName() {
        return null;
    }
}
