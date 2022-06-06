package com.bot.eyelashes.handler.callbackquery;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

public interface Callback {

    SendMessage getCallbackQuery(CallbackQuery callbackQuery);

//    List<SendMessage> getListCallbackQuery(CallbackQuery callbackQuery);

    InlineKeyboardMarkup getHandlerQueryType();
}
