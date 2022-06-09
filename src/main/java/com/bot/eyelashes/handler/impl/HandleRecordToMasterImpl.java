package com.bot.eyelashes.handler.impl;

import com.bot.eyelashes.handler.Handle;
import com.bot.eyelashes.repository.MasterRepository;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class HandleRecordToMasterImpl implements Handle {

    private final MasterRepository masterRepository;

    @Override
    public SendMessage getMessage(Update update) {
        return null;
    }

    public InlineKeyboardMarkup createInlineKeyboardButtonWithCallback(CallbackQuery callbackQuery){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
            buttons.add(Arrays.asList(
                    InlineKeyboardButton.builder()
                            .text("Записаться")
                            .callbackData("RECORD")
                            .build(),
                    InlineKeyboardButton.builder()
                            .text("Свзяаться")
                            .url("https://t.me/")
                            .build()));
            inlineKeyboardMarkup.setKeyboard(buttons);
            return inlineKeyboardMarkup;
    }

    @Override
    public InlineKeyboardMarkup createInlineKeyboard() {
        return null;
    }
}
