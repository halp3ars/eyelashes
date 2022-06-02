package com.bot.eyelashes.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Handle {
    SendMessage handleCallbackButton(CallbackQuery callbackQuery);
    SendMessage getMessage(Message message);

}
