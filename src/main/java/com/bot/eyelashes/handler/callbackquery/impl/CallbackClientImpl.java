package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.handler.impl.HandleClientImpl;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;


public class CallbackClientImpl implements Callback {

    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        HandleClientImpl handleClient = new HandleClientImpl();
        return SendMessage.builder()
                .chatId(callbackQuery.getMessage()
                        .getChatId()
                        .toString())
                .replyMarkup(handleClient.createInlineKeyboard())
                .text("Выберите вид деятельности")
                .build();
    }
}
