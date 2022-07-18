package com.bot.eyelashes.handler.impl;

import com.bot.eyelashes.handler.Handle;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
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

        final int MAX_WORK_TIME = 20;
        for (int hours = 8; hours < MAX_WORK_TIME; hours++) {
            if (hours <= 11) {
                rowMain.add(InlineKeyboardButton.builder()
                        .text(hours + " : 00")
                        .callbackData("TIME_TO/" + hours)
                        .build());
            } else if (hours <= 15) {
                rowSecond.add(InlineKeyboardButton.builder()
                        .text(hours + " : 00")
                        .callbackData("TIME_TO/" + hours)
                        .build());
            } else {
                rowThird.add(InlineKeyboardButton.builder()
                        .text(hours + " : 00")
                        .callbackData("TIME_TO/" + hours)
                        .build());
            }
        }

        return InlineKeyboardMarkup.builder()
                .keyboardRow(rowMain)
                .keyboardRow(rowSecond)
                .keyboardRow(rowThird)
                .build();
    }
}
