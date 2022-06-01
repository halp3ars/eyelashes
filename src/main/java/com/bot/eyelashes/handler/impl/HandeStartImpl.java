package com.bot.eyelashes.handler.impl;

import com.bot.eyelashes.handler.Handle;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
@Service
public class HandeStartImpl implements Handle {


    private final String MESSAGE_START_TEXT = "Здравствуйте, выберите одну из кнопок для дальнейщих дейсвтвий";
    private final String MESSAGE_MAIN_TEXT = "Выберите кем вы являетесь";

    @Override
    public SendMessage handleCallbackButton(CallbackQuery callbackQuery) {
        SendMessage sendMessage = new SendMessage();
        if(callbackQuery.getData().equals("main")) {
            Message message = callbackQuery.getMessage();
            List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
            buttons.add(
                    Arrays.asList(
                            InlineKeyboardButton.builder()
                                    .text("Клиент")
                                    .callbackData("client")
                                    .build(),
                            InlineKeyboardButton.builder()
                                    .text("Мастер")
                                    .callbackData("master")
                                    .build()));
            sendMessage = SendMessage.builder().chatId(message.getChatId().toString()).replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build()).text(MESSAGE_MAIN_TEXT).build();
        }
        return sendMessage;
    }

    @Override
    public SendMessage getMessage(Update update) {
        Message message = update.getMessage();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(
                Arrays.asList(
                        InlineKeyboardButton.builder()
                                .text("Информация о боте")
                                .callbackData("info")
                                .build(),
                        InlineKeyboardButton.builder()
                                .text("Начать работу")
                                .callbackData("main")
                                .build()));
        return SendMessage.builder().chatId(message.getChatId().toString()).replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build()).text(MESSAGE_START_TEXT).build();

    }
}
