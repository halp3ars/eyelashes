package com.bot.eyelashes.handler.callbackquery;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public interface Callback {

    SendMessage getCallbackQuery(CallbackQuery callbackQuery);

    InlineKeyboardMarkup getHandlerQueryType();
}
