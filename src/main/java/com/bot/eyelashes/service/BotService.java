package com.bot.eyelashes.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BotService extends TelegramLongPollingBot {


    @Value("${telegram.name}")
    private String name;

    @Value("${telegram.token}")
    private String token;


    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (update.hasMessage() && message.getText().equals("/start")) {
            handleInfo(update.getMessage());
        } else if (update.hasCallbackQuery()) {
            handleCallback(update.getCallbackQuery());
        }


    }

    private void handleInfo(Message message) throws TelegramApiException {
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

        execute(
                SendMessage.builder()
                        .text("Здраствуйте выберите одну из функций")
                        .chatId(message.getChatId().toString())
                        .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                        .build());

    }

    @SneakyThrows
    private void handleCallback(CallbackQuery callbackQuery) {
        if (callbackQuery.getData().equals("main")) {
            List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
            buttons.add(
                    Arrays.asList(
                            InlineKeyboardButton.builder()
                                    .text("Мастер")
                                    .callbackData("master")
                                    .build(),
                            InlineKeyboardButton.builder()
                                    .text("client")
                                    .callbackData("das")
                                    .build()));
            execute(
                    SendMessage.builder()
                            .text("Здраствуйте выберите одну из функций")
                            .chatId(callbackQuery.getMessage().getChatId().toString())
                            .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                            .build());
        } else if (callbackQuery.getData().equals("info")){
            List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
            buttons.add(
                    Arrays.asList(
                            InlineKeyboardButton.builder()
                                    .text("Главаная")
                                    .callbackData("main")
                                    .build(),
                            InlineKeyboardButton.builder()
                                    .text("Назад")
                                    .callbackData("back")
                                    .build()));
            execute(
                    SendMessage.builder()
                            .text("Информация о боте")
                            .chatId(callbackQuery.getMessage().getChatId().toString())
                            .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                            .build());
        }


    }

    @Override
    public String getBotUsername() {
        return name;
    }


    @Override
    public String getBotToken() {
        return token;
    }

}
