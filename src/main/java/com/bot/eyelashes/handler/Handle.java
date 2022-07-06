package com.bot.eyelashes.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public interface Handle {
    SendMessage getMessage(Update update);
    InlineKeyboardMarkup createInlineKeyboard();

}
