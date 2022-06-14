package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.handler.callbackquery.Callback;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CallbackMasterImpl implements Callback {
    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        return messageWithKeyboard(callbackQuery.getMessage().getChatId());
    }

    private SendMessage messageWithKeyboard(Long chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(Arrays.asList(
                InlineKeyboardButton.builder()
                        .text("Регистрация")
                        .callbackData("REGISTRATION")
                        .build()
        ));
        inlineKeyboardMarkup.setKeyboard(buttons);

        return SendMessage.builder()
                .text("Для регистрации нажмите на кнопку")
                .chatId(chatId.toString())
                .replyMarkup(inlineKeyboardMarkup)
                .build();
    }
}