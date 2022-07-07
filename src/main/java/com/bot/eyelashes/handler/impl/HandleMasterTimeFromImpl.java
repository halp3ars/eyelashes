package com.bot.eyelashes.handler.impl;

import com.bot.eyelashes.handler.Handle;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class HandleMasterTimeFromImpl implements Handle {


    @Override
    public SendMessage getMessage(Update update) {
        return null;

    }

    @Override
    public InlineKeyboardMarkup createInlineKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<Integer> workHours = new ArrayList<>();
        IntStream.range(8, 22)
                .forEach(workHours::add);
        workHours.forEach(timeText -> buttons.add(List.of(InlineKeyboardButton.builder()
                .text(timeText.toString())
                .callbackData("TIME_TO/" + timeText)
                .build())));
        inlineKeyboardMarkup.setKeyboard(buttons);
        return inlineKeyboardMarkup;
    }
}
