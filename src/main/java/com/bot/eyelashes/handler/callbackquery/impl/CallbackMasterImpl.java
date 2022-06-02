package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.handler.callbackquery.Callback;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class CallbackMasterImpl implements Callback {
    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        return null;
    }

    @Override
    public InlineKeyboardMarkup getHandlerQueryType() {
        return null;
    }
}
