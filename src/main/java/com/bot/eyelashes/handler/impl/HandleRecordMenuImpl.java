package com.bot.eyelashes.handler.impl;

import com.bot.eyelashes.cache.ClientDataCache;
import com.bot.eyelashes.enums.ClientBotState;
import com.bot.eyelashes.handler.Handle;
import com.bot.eyelashes.repository.ClientRepository;
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
public class HandleRecordMenuImpl implements Handle {

    private final MasterRepository masterRepository;
    public static Long masterId;

    @Override
    public SendMessage getMessage(Update update) {
        return null;
    }

    @Override
    public InlineKeyboardMarkup createInlineKeyboard() {
        return null;
    }

    public InlineKeyboardMarkup createInlineKeyboardWithCallback(CallbackQuery callbackQuery) {
        String callbackData = callbackQuery.getData();
        String callBackMasterId = callbackData.substring(callbackData.lastIndexOf("/") + 1);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        Long id = masterRepository.findById(Long.parseLong(callBackMasterId))
                .get()
                .getId();
        String telegramNick = masterRepository.findById(id)
                .get()
                .getTelegramNick();
        masterId = masterRepository.findById(id)
                .get()
                .getTelegramId();


            buttons.add(Arrays.asList(
                    InlineKeyboardButton.builder()
                            .text("Записаться")
                            .callbackData("RECORD")
                            .build(),
                    InlineKeyboardButton.builder()
                            .text("Свзяаться")
                            .url("https://t.me/" + telegramNick)
                            .build()));
            inlineKeyboardMarkup.setKeyboard(buttons);
            return inlineKeyboardMarkup;
    }
}
