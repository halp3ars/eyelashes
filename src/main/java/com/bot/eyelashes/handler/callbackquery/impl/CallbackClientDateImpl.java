package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.cache.ClientDataCache;
import com.bot.eyelashes.enums.ClientBotState;
import com.bot.eyelashes.handler.ClientBotStateContext;
import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.model.dto.RecordToMasterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;


@Service("CallbackDateClientImpl")
@RequiredArgsConstructor
public class CallbackClientDateImpl implements Callback {


    private final ClientDataCache clientDataCache;
    private final ClientBotStateContext clientBotStateContext;

    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage()
                .getChatId();
        RecordToMasterDto recordToMasterDto = clientDataCache.getRecordData(chatId);
        recordToMasterDto.setDay(callbackQuery.getData()
                .split("/")[1]);
        ClientBotState clientBotState = clientDataCache.getClientBotState(callbackQuery.getMessage()
                .getChatId());
        clientDataCache.setClientBotState(chatId, clientBotState);
        clientDataCache.saveRecordData(chatId, recordToMasterDto);
        if (clientBotState.equals(ClientBotState.ASK_CLIENT_TIME)) {
            return clientBotStateContext.processInputClientMessage(clientBotState, callbackQuery.getMessage());
        } else {
            return clientBotStateContext.processInputClientMessage(ClientBotState.NONE, callbackQuery.getMessage());
        }
    }
}
