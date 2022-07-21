package com.bot.eyelashes.handler.impl;

import com.bot.eyelashes.handler.Handle;
import com.bot.eyelashes.repository.RecordToMasterRepository;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class HandleClientAllChangeDateImpl implements Handle {


    private final RecordToMasterRepository record;


    @Override
    public SendMessage getMessage(Update update) {
        return null;
    }

    @Override
    public InlineKeyboardMarkup createInlineKeyboard() {
        return null;
    }

    public InlineKeyboardMarkup createInlineKeyboard(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage()
                .getChatId();
        List<InlineKeyboardButton> recordRow = new ArrayList<>();
        List<InlineKeyboardButton> backRow = new ArrayList<>();
        record.findAllByClientId(chatId)
                .forEach(record -> recordRow.add(InlineKeyboardButton.builder()
                        .text(record.getActivity() + " | " + record.getDay())
                        .callbackData("CHANGE_DATE/" + record.getActivity())
                        .build()));
        backRow.add(InlineKeyboardButton.builder()
                .callbackData("ALL_RECORDS")
                .text("Назад")
                .build());
        return InlineKeyboardMarkup.builder()
                .keyboardRow(recordRow)
                .keyboardRow(backRow)
                .build();
    }


}
