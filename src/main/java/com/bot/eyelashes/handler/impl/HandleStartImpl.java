package com.bot.eyelashes.handler.impl;

import com.bot.eyelashes.handler.Handle;
import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.handler.callbackquery.impl.CallbackMasterScheduleImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class HandleStartImpl implements Handle {
    private final String START_MESSAGE = "Здравствуйте это бот для записи на процедуры в салоне красоты";

    @Override
    public SendMessage getMessage(Update update) {
        return SendMessage.builder()
                .chatId(update.getMessage()
                        .getChatId()
                        .toString())
                .replyMarkup(createInlineKeyboard())
                .text(START_MESSAGE)
                .build();
    }

    @Override
    public InlineKeyboardMarkup createInlineKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(Arrays.asList(
                InlineKeyboardButton.builder()
                        .text("Начать")
                        .callbackData("MENU")
                        .build(),
                InlineKeyboardButton.builder()
                        .text("Список комманд")
                        .callbackData("INFO")
                        .build()));
        inlineKeyboardMarkup.setKeyboard(buttons);
        return inlineKeyboardMarkup;
    }

}
