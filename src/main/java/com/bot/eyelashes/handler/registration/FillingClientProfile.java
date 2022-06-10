package com.bot.eyelashes.handler.registration;

import com.bot.eyelashes.cache.ClientDataCache;
import com.bot.eyelashes.enums.BotState;
import com.bot.eyelashes.enums.ClientBotState;
import com.bot.eyelashes.model.dto.ClientDto;
import com.bot.eyelashes.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

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
            clientDataCache.setClientBotState(userId, ClientBotState.PROFILE_CLIENT_FIELD);
        }
        if (clientBotState.equals(ClientBotState.PROFILE_CLIENT_FIELD)) {
            clientDto.setPhoneNumber(clientAnswer);
            clientDto.setTelegramId(userId);
            clientDataCache.setClientBotState(userId, ClientBotState.CLIENT_REGISTRED);
            replyToClient = SendMessage.builder()
                    .text("Ваши данные -")
                    .chatId(chatId.toString())
                    .build();
        }
        if (clientBotState.equals(ClientBotState.CLIENT_REGISTRED)) {
            clientDataCache.setClientIntoDb(clientDto);
            replyToClient = SendMessage.builder().text("ВСЕ УСПЕШНО")
                    .chatId(chatId.toString())
                    .build();
        }


        clientDataCache.saveClientProfileData(userId, clientDto);
        return replyToClient;
    }


    public ClientBotState getHandleClientName(){
        return ClientBotState.FILLING_CLIENT_PROFILE;
    }

    @Override
    public BotState getHandleName() {
        return null;
    }
}
