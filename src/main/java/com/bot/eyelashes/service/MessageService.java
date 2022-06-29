package com.bot.eyelashes.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

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

    public SendMessage getReplyMessageForService(Long chatId, String text) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(List.of(
                InlineKeyboardButton.builder()
                        .text("Брови")
                        .callbackData("Брови")
                        .build(),
                InlineKeyboardButton.builder()
                        .text("Ресницы")
                        .callbackData("Ресницы")
                        .build(),
                InlineKeyboardButton.builder()
                        .text("Ногти")
                        .callbackData("Ногти")
                        .build()
        ));

        InlineKeyboardMarkup keyboardService = new InlineKeyboardMarkup();
        keyboardService.setKeyboard(buttons);

        return SendMessage.builder()
                .text(text)
                .chatId(chatId.toString())
                .replyMarkup(keyboardService)
                .build();
    }

    public SendMessage getReplyMessageForSchedule(Long chatId, String text) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(List.of(
                InlineKeyboardButton.builder()
                        .callbackData("SCHEDULE")
                        .text("Расписание")
                        .build()
        ));

        InlineKeyboardMarkup keyboardSchedule = InlineKeyboardMarkup.builder()
                .keyboard(buttons)
                .build();

        return SendMessage.builder()
                .text(text)
                .replyMarkup(keyboardSchedule)
                .chatId(chatId.toString())
                .build();
    }

//    public SendMessage get
}
