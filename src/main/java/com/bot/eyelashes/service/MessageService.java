package com.bot.eyelashes.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    public SendMessage getReplyMessage(Long chatId, String text) {
        return SendMessage.builder()
                .text(text)
                .chatId(chatId.toString())
                .build();
    }

    public SendMessage getReplyMessageForContact(Long chatId, String text) {
        return SendMessage.builder()
                .text(text)
                .chatId(chatId.toString())
                .replyMarkup(keyboardContact())
                .build();
    }

    public ReplyKeyboardMarkup keyboardContact() {
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

    public SendMessage getReplyMessageWithKeyboard(Long chatId, String text, InlineKeyboardMarkup keyboard) {
        return SendMessage.builder()
                .chatId(chatId.toString())
                .text(text)
                .replyMarkup(keyboard)
                .build();
    }
}
