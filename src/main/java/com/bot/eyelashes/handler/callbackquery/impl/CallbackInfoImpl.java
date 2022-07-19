package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.handler.impl.HandleInfoImpl;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public class CallbackInfoImpl implements Callback {

    private final String INFO_TEXT = "Список коммнад:\n/start - начать работу с ботом\n/info - получить список коммнад\n/menu - перейти к фукнционалу\n/client - меню клиента \n/master - список записей (Для мастера)";

    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        HandleInfoImpl handleInfo = new HandleInfoImpl();
        return SendMessage.builder()
                .chatId(callbackQuery.getMessage().getChatId()
                        .toString())
                .text(INFO_TEXT)
                .replyMarkup(handleInfo.createInlineKeyboard())
                .build();
    }
}
