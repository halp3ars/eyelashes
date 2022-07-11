package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.cache.ClientDataCache;
import com.bot.eyelashes.enums.ClientBotState;
import com.bot.eyelashes.handler.ClientBotStateContext;
import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.model.dto.RecordToMasterDto;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@RequiredArgsConstructor
public class CallbackClientPhoneImpl implements Callback {

    private  final ClientDataCache clientDataCache;
    private final ClientBotStateContext clientBotStateContext;

    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        ClientBotState clientBotState = ClientBotState.ASK_CLIENT_TIME;
        clientDataCache.setClientBotState(callbackQuery.getMessage()
                .getChatId(), clientBotState);
        return clientBotStateContext.processInputClientMessage(clientBotState, callbackQuery.getMessage());
    }
}
