package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.cache.ClientDataCache;
import com.bot.eyelashes.enums.ClientBotState;
import com.bot.eyelashes.handler.ClientBotStateContext;
import com.bot.eyelashes.handler.callbackquery.Callback;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;


@Service("CallbackTimeClientImpl")
@RequiredArgsConstructor
public class CallbackTimeClientImpl implements Callback {

    private final ClientDataCache clientDataCache;
    private final ClientBotStateContext clientBotStateContext;
    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        ClientBotState clientBotState = ClientBotState.ASK_CLIENT_TIME;
        clientDataCache.setClientBotState(callbackQuery.getMessage()
                .getChatId(), clientBotState);
//        clientBotStateContext.processInputClientMessage(clientBotState, update);
        return null;
    }
}
