package com.bot.eyelashes.handler.callbackquery;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface Callback {

    SendMessage getCallbackQuery(CallbackQuery callbackQuery);

    void getHandlerQueryType();
}
