package com.bot.eyelashes.handler.registration;

import com.bot.eyelashes.cache.ClientDataCache;
import com.bot.eyelashes.enums.BotState;
import com.bot.eyelashes.enums.ClientBotState;
import com.bot.eyelashes.handler.impl.HandleRecordMenuImpl;
import com.bot.eyelashes.model.dto.ClientDto;
import com.bot.eyelashes.model.dto.RecordToMasterDto;
import com.bot.eyelashes.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FillingClientProfile implements HandleRegistration {


    private final ClientDataCache clientDataCache;

    private final MessageService messageService;

    @Override
    public SendMessage getMessage(Message message) {
        if (clientDataCache.getClientBotState(message.getFrom()
                        .getId())
                .equals(ClientBotState.FILLING_CLIENT_PROFILE)) {
            clientDataCache.setClientBotState(message.getFrom()
                    .getId(), ClientBotState.ASK_CLIENT_FULL_NAME);
        }
        return processClientInput(message);
    }


    private SendMessage processClientInput(Message message) {
        String clientAnswer = message.getText();
        Long userId = message.getFrom()
                .getId();
        Long chatId = message.getChatId();
        ClientDto clientDto = clientDataCache.getClientProfileData(userId);
        ClientBotState clientBotState = clientDataCache.getClientBotState(userId);
        RecordToMasterDto recordToMasterDto = clientDataCache.getRecordData(userId);
        SendMessage replyToClient = null;
        if (clientBotState.equals(ClientBotState.ASK_CLIENT_FULL_NAME)) {
            replyToClient = messageService.getReplyMessage(chatId, "Введите ФИО через пробел");
            clientDataCache.setClientBotState(userId, ClientBotState.ASK_CLIENT_PHONE);
        }
        if (clientBotState.equals(ClientBotState.ASK_CLIENT_PHONE)) {

            String[] fullClientName = clientAnswer.split(" ");
            clientDto.setName(fullClientName[1]);
            clientDto.setMiddleName(fullClientName[2]);
            clientDto.setSurname(fullClientName[0]);
            replyToClient = messageService.getReplyMessage(chatId, "Введите телефон начиная с +7");
            clientDataCache.setClientBotState(userId, ClientBotState.ASK_CLIENT_DATE);
        }
        if (clientBotState.equals(ClientBotState.ASK_CLIENT_DATE)) {
            clientDto.setPhoneNumber(clientAnswer);
            clientDto.setTelegramId(userId);
            clientDataCache.setClientBotState(userId, ClientBotState.ASK_CLIENT_TIME);
            replyToClient = SendMessage.builder()
                    .text("Введите дату записи в формате 2022-11-11")
                    .chatId(chatId.toString())
                    .build();
        }
        if (clientBotState.equals(ClientBotState.ASK_CLIENT_TIME)) {
            recordToMasterDto.setDate(LocalDate.parse(clientAnswer));
            clientDataCache.setClientBotState(userId, ClientBotState.PROFILE_CLIENT_FIELD);
            replyToClient = SendMessage.builder()
                    .text("Введите время в формате 00:00")
                    .chatId(chatId.toString())
                    .build();
        }
        if (clientBotState.equals(ClientBotState.PROFILE_CLIENT_FIELD)) {
            recordToMasterDto.setMasterId(HandleRecordMenuImpl.masterId);
            recordToMasterDto.setTime(clientAnswer);
            recordToMasterDto.setClientId(userId);
            clientDataCache.setClientBotState(userId, ClientBotState.CLIENT_REGISTRED);
            replyToClient = SendMessage.builder()
                    .text("Вы записаны на " + recordToMasterDto.getDate() + " " + recordToMasterDto.getTime() + "\n Напишите что-либо для продолжения")
                    .chatId(chatId.toString())
                    .build();
        }
        if (clientBotState.equals(ClientBotState.CLIENT_REGISTRED)) {
            clientDataCache.setClientIntoDb(clientDto);
            clientDataCache.setClientRecord(recordToMasterDto);
            replyToClient = SendMessage.builder().text("Выберите что вы хотите сделать далее").replyMarkup(createInlineMarkupLastMessage())
                    .chatId(chatId.toString())
                    .build();
        }
        clientDataCache.saveRecordData(userId, recordToMasterDto);
        clientDataCache.saveClientProfileData(userId, clientDto);
        return replyToClient;
    }


    public ClientBotState getHandleClientName() {
        return ClientBotState.FILLING_CLIENT_PROFILE;
    }


    private InlineKeyboardMarkup createInlineMarkupLastMessage() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(Arrays.asList(
                InlineKeyboardButton.builder().text("Готов").callbackData("CHECK_RECORD").build(),
                InlineKeyboardButton.builder().text("На главную").callbackData("MENU").build()
        ));
        inlineKeyboardMarkup.setKeyboard(buttons);
        return inlineKeyboardMarkup;
    }


    @Override
    public BotState getHandleName() {
        return null;
    }
}
