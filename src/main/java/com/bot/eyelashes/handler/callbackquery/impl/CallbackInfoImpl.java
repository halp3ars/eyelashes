package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.handler.callbackquery.Callback;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public class CallbackInfoImpl implements Callback {

    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        return SendMessage.builder().chatId(callbackQuery.getMessage().getChatId().toString()).text("INFO_CALLBACK").build();
    }

    @Override
    public void getHandlerQueryType() {

    }
}
