package com.bot.eyelashes.handler.impl;

import com.bot.eyelashes.handler.Handle;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HandleInfoImpl implements Handle {
    @Override
    public SendMessage getMessage(Update update) {
        return SendMessage.builder()
                .chatId(update.getMessage().getChatId()
                        .toString())
                .text("Amd")
                .replyMarkup(InlineKeyboardMarkup.builder()
                        .keyboard(createInlineKeyboard().getKeyboard())
                        .build())
                .build();

    }

    @Override
    public InlineKeyboardMarkup createInlineKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(List.of(
                InlineKeyboardButton.builder()
                        .text("Мастер")
                        .callbackData("MENU")
                        .build()));
        inlineKeyboardMarkup.setKeyboard(buttons);
        return inlineKeyboardMarkup;
    }
}
