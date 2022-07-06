package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.handler.impl.HandleMasterTimeFromImpl;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public class CallbackMasterTimeFromImpl implements Callback{


    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        HandleMasterTimeFromImpl handleMasterTimeFrom = new HandleMasterTimeFromImpl();
        return SendMessage.builder().text("Выберите с какго времени вы будете рабоать").replyMarkup(handleMasterTimeFrom.createInlineKeyboard())
                .chatId(callbackQuery.getMessage().getChatId().toString()).build();
    }
}
