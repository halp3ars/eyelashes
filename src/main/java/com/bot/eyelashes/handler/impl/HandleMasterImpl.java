package com.bot.eyelashes.handler.impl;

import com.bot.eyelashes.handler.Handle;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HandleMasterImpl implements Handle {

    @Override
    public SendMessage getMessage(Message message) {
        return SendMessage.builder()
                .text("Введите данные")
                .chatId(message.getChatId()
                        .toString())
                .replyMarkup(createInlineKeyboard())
                .build();
    }


    @Override
    public InlineKeyboardMarkup createInlineKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(Arrays.asList(
                InlineKeyboardButton.builder()
                        .text("Фамилия")
                        .callbackData("Surname")
                        .build(),
                InlineKeyboardButton.builder()
                        .text("Имя")
                        .callbackData("Name")
                        .build(),
                InlineKeyboardButton.builder()
                        .text("Отчество")
                        .callbackData("MiddleName")
                        .build(),
                InlineKeyboardButton.builder()
                        .text("Адрес")
                        .callbackData("Address")
                        .build(),
                InlineKeyboardButton.builder()
                        .text("Номер телефона")
                        .callbackData("phone")
                        .build()
        ));

        inlineKeyboardMarkup.setKeyboard(buttons);
        return inlineKeyboardMarkup;
    }
}
