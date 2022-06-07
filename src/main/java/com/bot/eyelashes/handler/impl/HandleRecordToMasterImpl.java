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

    @Override
    public InlineKeyboardMarkup createInlineKeyboard() {
        return null;
    }

    public InlineKeyboardMarkup createInlineKeyboardWithCallback(CallbackQuery callbackQuery) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        String callbackData = callbackQuery.getData();
        String callBackId = callbackData.substring(callbackData.lastIndexOf("/") + 1);
        Long id = masterRepository.findById(Long.parseLong(callBackId)).get()
                .getId();
        String phoneNumber = masterRepository.findById(id).get().getPhoneNumber();
        buttons.add(Arrays.asList(
                InlineKeyboardButton.builder()
                        .text("Записаться")
                        .callbackData("RECORD")
                        .build(),
                InlineKeyboardButton.builder()
                        .text("Свзяаться")
                        .url("https://t.me/" + phoneNumber)
                        .build()));
        inlineKeyboardMarkup.setKeyboard(buttons);
        return inlineKeyboardMarkup;
    }

}
