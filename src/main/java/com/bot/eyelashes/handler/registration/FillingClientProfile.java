package com.bot.eyelashes.handler.registration;

import com.bot.eyelashes.cache.ClientDataCache;
import com.bot.eyelashes.enums.BotState;
import com.bot.eyelashes.enums.ClientBotState;
import com.bot.eyelashes.handler.callbackquery.impl.CallbackTypeOfActivityImpl;
import com.bot.eyelashes.handler.impl.HandleClientTimeImpl;
import com.bot.eyelashes.handler.impl.HandleRecordMenuImpl;
import com.bot.eyelashes.handler.impl.HandleClientScheduleImpl;
import com.bot.eyelashes.mapper.ScheduleMapper;
import com.bot.eyelashes.model.dto.ClientDto;
import com.bot.eyelashes.model.dto.RecordToMasterDto;
import com.bot.eyelashes.repository.ClientRepository;
import com.bot.eyelashes.repository.RecordToMasterRepository;
import com.bot.eyelashes.repository.ScheduleRepository;
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
import java.util.Arrays;
import java.util.List;


@Component
@RequiredArgsConstructor
@Slf4j
public class FillingClientProfile implements HandleRegistration {


    private final ClientDataCache clientDataCache;
    private final MessageService messageService;
    private final ScheduleMapper scheduleMapper;
    private final ScheduleRepository scheduleRepository;
    private final RecordToMasterRepository recordToMasterRepository;
    private final ClientRepository clientRepository;

    @Override
    public SendMessage getMessage(Message message) {
        if (clientDataCache.getClientBotState(message.getChatId())
                .equals(ClientBotState.FILLING_CLIENT_PROFILE)) {
            clientDataCache.setClientBotState(message.getChatId(), ClientBotState.ASK_CLIENT_NAME);
        }
        return processClientInput(message);
    }


    private SendMessage processClientInput(Message message) {
        String clientAnswer = message.getText();
        Long chatId = message.getChatId();
        ClientDto clientDto = clientDataCache.getClientProfileData(chatId);
        ClientBotState clientBotState = clientDataCache.getClientBotState(chatId);
        RecordToMasterDto recordToMasterDto = clientDataCache.getRecordData(chatId);
        SendMessage replyToClient = null;
        if (clientBotState.equals(ClientBotState.ASK_CLIENT_NAME)) {
            replyToClient = messageService.getReplyMessage(chatId, "Введите Имя");
            clientDataCache.setClientBotState(chatId, ClientBotState.ASK_CLIENT_SURNAME);
        }
        if (clientBotState.equals(ClientBotState.ASK_CLIENT_SURNAME)) {
            clientDto.setName(clientAnswer);
            replyToClient = messageService.getReplyMessage(chatId, "Введите Фамилию");
            clientDataCache.setClientBotState(chatId, ClientBotState.ASK_CLIENT_PHONE);

        }
        if (clientBotState.equals(ClientBotState.ASK_CLIENT_PHONE)) {
            clientDto.setTelegramNick(message.getFrom()
                    .getUserName());
            clientDto.setSurname(clientAnswer);
            replyToClient = messageService.getReplyMessage(chatId, "Введите телефон");
            clientDataCache.setClientBotState(chatId, ClientBotState.ASK_CLIENT_DATE);
        }
        if (clientBotState.equals(ClientBotState.ASK_CLIENT_DATE)) {
            clientDto.setTelegramId(chatId);
            recordToMasterDto.setActivity(CallbackTypeOfActivityImpl.activity.get(message.getChatId()));
            clientDto.setPhoneNumber(clientAnswer);
            clientDataCache.setClientBotState(chatId, ClientBotState.ASK_CLIENT_TIME);
            HandleClientScheduleImpl handleScheduleClient = new HandleClientScheduleImpl(scheduleMapper, scheduleRepository);
            replyToClient = SendMessage.builder()
                    .text("Выберите день на неделе")
                    .replyMarkup(handleScheduleClient.createInlineKeyboard())
                    .chatId(chatId.toString())
                    .build();
        }
        if (clientBotState.equals(ClientBotState.ASK_CLIENT_TIME)) {
            clientDataCache.setClientBotState(chatId, ClientBotState.PROFILE_CLIENT_FIELD);
            HandleClientTimeImpl handleClientTime = new HandleClientTimeImpl(recordToMasterRepository, scheduleRepository);
            replyToClient = SendMessage.builder()
                    .text("Выберите время")
                    .replyMarkup(handleClientTime.createInlineKeyboard())
                    .chatId(chatId.toString())
                    .build();
        }
        if (clientBotState.equals(ClientBotState.PROFILE_CLIENT_FIELD)) {
            recordToMasterDto.setMasterId(HandleRecordMenuImpl.masterId);
            recordToMasterDto.setClientId(chatId);
            if (clientRepository.findByTelegramId(chatId)
                    .isEmpty())
                clientDataCache.setClientIntoDb(clientDto);
            clientDataCache.setClientRecord(recordToMasterDto);
            replyToClient = SendMessage.builder()
                    .text("Вы записаны на " + recordToMasterDto.getDay() + " " + recordToMasterDto.getTime() + "\nНажмите какую-либо кнопку для продолжение")
                    .replyMarkup(createInlineMarkupLastMessage())
                    .chatId(chatId.toString())
                    .build();
            Bot.clientRegistration = false;
            clientDataCache.setClientBotState(chatId, ClientBotState.NONE);
        }
        clientDataCache.saveClientProfileData(chatId, clientDto);
        clientDataCache.saveRecordData(chatId, recordToMasterDto);
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
