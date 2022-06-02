package com.bot.eyelashes.handler.impl;

import com.bot.eyelashes.handler.Handle;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class HandleStartImpl implements Handle {

    private final String START_MESSAGE = "Здравствуйте это бот для записи на процедуры в салоне красоты";


    @Override
    public SendMessage getMessage(Update update) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        InlineKeyboardButton buttonStart = InlineKeyboardButton.builder()
                .text("Начать")
                .callbackData("INFO")
                .build();
        buttons.add(Arrays.asList(buttonStart));
        return SendMessage.builder()
                .chatId(update.getMessage()
                        .getChatId()
                        .toString())
                .replyMarkup(InlineKeyboardMarkup.builder()
                        .keyboard(buttons)
                        .build())
                .text(START_MESSAGE)
                .build();
    }
}
