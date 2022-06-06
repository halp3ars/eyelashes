package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.handler.callbackquery.Callback;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public class CallbackMasterImpl implements Callback {
    @Override
    public SendMessage getMessageByCallback(CallbackQuery callbackQuery) {
        return null;
    }

    @Override
    public InlineKeyboardMarkup getMarkup() {
        return null;
    }
}
