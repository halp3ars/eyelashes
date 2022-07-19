package com.bot.eyelashes.handler.impl;

import com.bot.eyelashes.handler.Handle;
import com.bot.eyelashes.enums.map.TypeOfActivity;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HandleClientImpl implements Handle {

    @Override
    public SendMessage getMessage(Update update) {
        return SendMessage.builder()
                .chatId(update.getMessage()
                        .getChatId()
                        .toString())
                .replyMarkup(createInlineKeyboard())
                .text("Выберите вид деятельности")
                .build();
    }

    @Override
    public InlineKeyboardMarkup createInlineKeyboard() {
        TypeOfActivity typeOfActivity = new TypeOfActivity();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.addAll(List.of(InlineKeyboardButton.builder()
                        .text(typeOfActivity.getCommand("EYEBROWS"))
                        .callbackData("EYEBROWS")
                        .build(),
                InlineKeyboardButton.builder()
                        .text(typeOfActivity.getCommand("EYELASHES"))
                        .callbackData("EYELASHES")
                        .build(),
                InlineKeyboardButton.builder()
                        .text(typeOfActivity.getCommand("NAILS"))
                        .callbackData("NAILS")
                        .build()));
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        row2.add(InlineKeyboardButton.builder()
                .text("Мои записи")
                .callbackData("ALL_RECORDS")
                .build());
        return InlineKeyboardMarkup.builder()
                .keyboardRow(row1)
                .keyboardRow(row2)
                .build();
    }
}
