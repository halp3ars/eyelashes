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

    private final TelegramProperties telegramProperties;
    @Override
    public SendMessage handleCallbackButton(CallbackQuery callbackQuery) {
        if(callbackQuery.getData().equals("main")){

        }
        return SendMessage.builder().build();
    }

    @Override
    public SendMessage getMessage(Update update) {
        List<List<InlineKeyboardButton>> buttonMainMenu =  new ArrayList<>();
        buttonMainMenu.add(
                Arrays.asList(
                        InlineKeyboardButton.builder()
                                .text("Меню")
                                .callbackData("menu")
                                .build()));
        return SendMessage.builder().text(telegramProperties.getMessageTextInfo()).replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttonMainMenu).build()).build();

    }
}
