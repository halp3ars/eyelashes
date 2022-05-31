package com.bot.eyelashes.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

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
        if(message.hasText()){
            if(message.getText().equals("/info")){
                execute(SendMessage.builder().text("Информация о нашем боте ").chatId(message.getChatId().toString()).build());
            }
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
