package com.bot.eyelashes.handler.callbackquery;

import com.bot.eyelashes.enums.BotState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface CallbackRegistration {
    SendMessage getCallbackQuery(CallbackQuery callbackQuery);
    BotState getHandleName();
}
