package com.bot.eyelashes.handler.impl;

import com.bot.eyelashes.handler.Handle;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class HandleInfoImpl implements Handle {
    @Override
    public void handleButton() {
        InlineKeyboardButton buttonMainMenu = InlineKeyboardButton.builder()
                .text("Меню")
                .callbackData("menu")
                .build();
    }

    @Override
    public SendMessage getMessage(Update update) {
        return new SendMessage();
    }
}
