package com.bot.eyelashes.service;

import com.bot.eyelashes.config.properties.TelegramProperties;
import com.bot.eyelashes.handler.impl.HandeStartImpl;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BotService extends TelegramLongPollingBot {


    private final TelegramProperties telegramProperties;
    private final HandeStartImpl startHandler;

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()){
            execute(startHandler.handleCallbackButton(update.getCallbackQuery()));
        }else if(update.getMessage().getText().equals("/start")){
               execute(startHandler.getMessage(update));
        }
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
        }else if (callbackQuery.getData().equals("info")){

        }


    }

    @Override
    public String getBotUsername() {
        return telegramProperties.getNameBot();
    }


    @Override
    public String getBotToken() {
        return telegramProperties.getTokenBot();
    }

}
