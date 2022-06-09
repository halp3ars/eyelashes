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
                .chatId(update.getMessage().getChatId()
                        .toString())
                .text("Выберите вид деятельности")
                .replyMarkup(createInlineKeyboard())
                .build();
    }


    @Override
    public InlineKeyboardMarkup createInlineKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        TypeOfActivity typeOfActivity = new TypeOfActivity();
        buttons.add(Arrays.asList(
                InlineKeyboardButton.builder()
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
        inlineKeyboardMarkup.setKeyboard(buttons);
        return inlineKeyboardMarkup;
    }
}
