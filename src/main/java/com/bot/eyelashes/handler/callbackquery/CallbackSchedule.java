package com.bot.eyelashes.handler.callbackquery;

import com.bot.eyelashes.enums.BotState;
import com.bot.eyelashes.enums.StateSchedule;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface CallbackSchedule {
    SendMessage getCallbackQuery(CallbackQuery callbackQuery);
    SendMessage getMessage(Message message);
    StateSchedule getHandleName();

}
