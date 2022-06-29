package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.cache.ClientDataCache;
import com.bot.eyelashes.enums.ClientBotState;
import com.bot.eyelashes.handler.callbackquery.Callback;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;


@Service("CallbackTimeClientImpl")
@RequiredArgsConstructor
public class CallbackTimeClientImpl implements Callback {

    private final ClientDataCache clientDataCache;

    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        clientDataCache.setClientBotState(callbackQuery.getMessage().getChatId(), ClientBotState.PROFILE_CLIENT_FIELD);
        return SendMessage.builder().text("asd").chatId(callbackQuery.getMessage().getChatId().toString()).build();
    }
}
