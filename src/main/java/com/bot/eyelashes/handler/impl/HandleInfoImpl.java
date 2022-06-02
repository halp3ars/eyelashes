package com.bot.eyelashes.handler.impl;

import com.bot.eyelashes.config.properties.TelegramProperties;
import com.bot.eyelashes.handler.Handle;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class HandleInfoImpl implements Handle {

    @Override
    public SendMessage getMessage(Update update) {
        List<List<InlineKeyboardButton>> buttonMainMenu = new ArrayList<>();
        buttonMainMenu.add(
                Arrays.asList(
                        InlineKeyboardButton.builder()
                                .text("Меню")
                                .callbackData("START")
                                .build()));
        return SendMessage.builder()
                .chatId(update.getMessage()
                        .getChatId()
                        .toString())
                .text("хкй")
                .replyMarkup(InlineKeyboardMarkup.builder()
                        .keyboard(buttonMainMenu)
                        .build())
                .build();

    }
}
