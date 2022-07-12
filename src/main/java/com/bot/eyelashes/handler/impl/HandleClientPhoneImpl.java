package com.bot.eyelashes.handler.impl;

import com.bot.eyelashes.cache.ClientDataCache;
import com.bot.eyelashes.enums.ClientBotState;
import com.bot.eyelashes.handler.Handle;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class HandleClientPhoneImpl implements Handle {

    private final ClientDataCache clientDataCache;

    @Override
    public SendMessage getMessage(Update update) {
        return null;
    }

    @Override
    public InlineKeyboardMarkup createInlineKeyboard() {
        return null;
    }

    public ReplyKeyboardMarkup keyboardContact(Message message) {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        List<KeyboardRow> row = new ArrayList<>();
        KeyboardRow rowAddress = new KeyboardRow();
        rowAddress.add(KeyboardButton.builder()
                .text("Номер телефона")
                .requestContact(true)
                .build());
        row.add(rowAddress);
        markup.setSelective(true);
        markup.setOneTimeKeyboard(true);
        markup.setResizeKeyboard(true);
        markup.setKeyboard(row);
        return markup;
    }
}
