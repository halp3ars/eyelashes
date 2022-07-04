package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.cache.ClientDataCache;
import com.bot.eyelashes.enums.ClientBotState;
import com.bot.eyelashes.handler.ClientBotStateContext;
import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.repository.ClientRepository;
import com.bot.eyelashes.service.Bot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@RequiredArgsConstructor
@Service("CallbackRecordImpl")
public class CallbackRecordImpl implements Callback {

    private final ClientDataCache clientDataCache;
    private final ClientBotStateContext clientBotStateContext;


    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        Bot.clientRegistration = true;
        ClientBotState clientBotState = ClientBotState.ASK_CLIENT_NAME;
        clientDataCache.setClientBotState(callbackQuery.getMessage()
                .getChatId(), clientBotState);
        return clientBotStateContext.processInputClientMessage(clientBotState, callbackQuery.getMessage());
    }
}
