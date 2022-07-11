package com.bot.eyelashes.handler.impl;

import com.bot.eyelashes.enums.map.TypeOfActivity;
import com.bot.eyelashes.handler.Handle;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

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
        List<InlineKeyboardButton> rowMain = new ArrayList<>();
        List<InlineKeyboardButton> rowSecond = new ArrayList<>();
        List<InlineKeyboardButton> rowThird = new ArrayList<>();
        List<InlineKeyboardButton> rowFourth = new ArrayList<>();

        for (int hours = 8; hours < 20; hours++) {
            if (hours <= 11) {
                rowMain.add(InlineKeyboardButton.builder()
                        .text(hours+ " : 00")
                        .callbackData("TIME_TO/" + hours)
                        .build());
            } else if (hours > 11 && hours <= 15) {
                rowSecond.add(InlineKeyboardButton.builder()
                        .text(hours + " : 00")
                        .callbackData("TIME_TO/" + hours)
                        .build());
            } else if (hours > 15 && hours <= 19) {
                rowThird.add(InlineKeyboardButton.builder()
                        .text(hours+ " : 00")
                        .callbackData("TIME_TO/" + hours)
                        .build());
            } else if (hours > 19) {
                rowFourth.add(InlineKeyboardButton.builder()
                        .text(hours+ " : 00")
                        .callbackData("TIME_TO/" + hours)
                        .build());
            }
        }

        return InlineKeyboardMarkup.builder()
                .keyboardRow(rowMain)
                .keyboardRow(rowSecond)
                .keyboardRow(rowThird)
                .keyboardRow(rowFourth)
                .build();
    }
}
