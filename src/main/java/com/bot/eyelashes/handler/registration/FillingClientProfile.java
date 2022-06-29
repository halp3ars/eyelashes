package com.bot.eyelashes.handler.registration;

import com.bot.eyelashes.cache.ClientDataCache;
import com.bot.eyelashes.enums.BotState;
import com.bot.eyelashes.enums.ClientBotState;
import com.bot.eyelashes.handler.impl.HandleRecordMenuImpl;
import com.bot.eyelashes.model.dto.ClientDto;
import com.bot.eyelashes.model.dto.RecordToMasterDto;
import com.bot.eyelashes.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
@Slf4j
public class FillingClientProfile implements HandleRegistration {


    private final ClientDataCache clientDataCache;

    private final MessageService messageService;

    @Override
    public SendMessage getMessage(Update update) {
        Message message;
        if (update.hasCallbackQuery()) {
            message = update.getCallbackQuery()
                    .getMessage();
        } else {
            message = update.getMessage();
        }

        if (clientDataCache.getClientBotState(message.getChatId())
                .equals(ClientBotState.FILLING_CLIENT_PROFILE)) {
            clientDataCache.setClientBotState(message.getChatId(), ClientBotState.ASK_CLIENT_NAME);
        }
        return processClientInput(update);
    }


    private SendMessage processClientInput(Update update) {
        Message message = update.getMessage();
        String clientAnswer;
        Long userId;
        Long chatId;
        if (update.hasCallbackQuery()) {
            clientAnswer = update.getCallbackQuery()
                    .getMessage()
                    .getText();
            chatId = update.getCallbackQuery()
                    .getMessage()
                    .getChatId();
            userId = update.getCallbackQuery()
                    .getMessage()
                    .getChatId();
        } else {
            clientAnswer = message.getText();
            userId = message.getFrom()
                    .getId();
            chatId = message.getChatId();
            log.info(userId + " " + chatId);
        }

        ClientDto clientDto = clientDataCache.getClientProfileData(userId);
        ClientBotState clientBotState = clientDataCache.getClientBotState(userId);
        RecordToMasterDto recordToMasterDto = clientDataCache.getRecordData(userId);
        SendMessage replyToClient = null;
        if (clientBotState.equals(ClientBotState.ASK_CLIENT_NAME)) {
            replyToClient = messageService.getReplyMessage(chatId, "Введите Имя");
            clientDataCache.setClientBotState(userId, ClientBotState.ASK_CLIENT_SURNAME);
        }
        if (clientBotState.equals(ClientBotState.ASK_CLIENT_SURNAME)) {
            clientDto.setName(clientAnswer);
            replyToClient = messageService.getReplyMessage(chatId, "Введите Фамилию");
            clientDataCache.setClientBotState(userId, ClientBotState.ASK_CLIENT_PHONE);

        } if (clientBotState.equals(ClientBotState.ASK_CLIENT_PHONE)) {
            clientDto.setSurname(clientAnswer);
            replyToClient = messageService.getReplyMessage(chatId, "Введите телефон начиная с +7");
            clientDataCache.setClientBotState(userId, ClientBotState.ASK_CLIENT_DATE);
        }

        if (clientBotState.equals(ClientBotState.ASK_CLIENT_DATE)) {
            clientDto.setTelegramNick(update.getMessage().getFrom().getUserName());
            if (FillingMasterProfile.isValidPhone(clientAnswer)) {
                clientDto.setTelegramId(userId);
                clientDataCache.setClientBotState(userId, ClientBotState.ASK_CLIENT_TIME);
                replyToClient = SendMessage.builder()
                        .text("Введите дату записи в формате 2022-06-15 (ГГГГ-ММ-ДД)")
                        .chatId(chatId.toString())
                        .build();
            }else {
                replyToClient = messageService.getReplyMessage(chatId, "Некорректный номер телефона");
            }
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
            clientDataCache.setClientIntoDb(clientDto);
            clientDataCache.setClientRecord(recordToMasterDto);
            replyToClient = SendMessage.builder()
                    .text("Вы записаны на " + recordToMasterDto.getDate() + " " + recordToMasterDto.getTime() + "\nНажмите какую-либо кнопку для продолжение")
                    .replyMarkup(createInlineMarkupLastMessage())
                    .chatId(chatId.toString())
                    .build();
            clientDataCache.setClientBotState(userId, ClientBotState.NONE);
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
                InlineKeyboardButton.builder()
                        .text("Готов")
                        .callbackData("CHECK_RECORD")
                        .build(),
                InlineKeyboardButton.builder()
                        .text("На главную")
                        .callbackData("MENU")
                        .build()
        ));
        inlineKeyboardMarkup.setKeyboard(buttons);
        return inlineKeyboardMarkup;
    }

    @Override
    public BotState getHandleName() {
        return null;
    }
}
