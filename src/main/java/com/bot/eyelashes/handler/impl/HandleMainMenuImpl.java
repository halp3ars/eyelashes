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

public class HandleMainMenuImpl implements Handle {


    @Override
    public SendMessage getMessage(Message message) {
        return SendMessage.builder()
                .replyMarkup(createInlineKeyboard())
                .chatId(message.getChatId()
                        .toString())
                .text("Меншечька")
                .build();
    }

    public InlineKeyboardMarkup createInlineKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(Arrays.asList(
                InlineKeyboardButton.builder()
                        .text("Клиент")
                        .callbackData("CLIENT")
                        .build(),
                InlineKeyboardButton.builder()
                        .text("Мастер")
                        .callbackData("MASTER")
                        .build()));
        inlineKeyboardMarkup.setKeyboard(buttons);
        return inlineKeyboardMarkup;
    }
}
