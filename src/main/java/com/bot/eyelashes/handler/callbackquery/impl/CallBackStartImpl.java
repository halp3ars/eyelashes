package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.handler.Handle;
import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.handler.impl.HandleInfoImpl;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public class CallBackStartImpl implements Callback {

    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        return SendMessage.builder().chatId(callbackQuery.getMessage().getChatId().toString()).text("START_CALLBACK").build();
    }

    @Override
    public void getHandlerQueryType() {

    }
}
