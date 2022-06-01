package com.bot.eyelashes.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Handle {
    void handleButton();
    SendMessage getMessage(Update update);
}
