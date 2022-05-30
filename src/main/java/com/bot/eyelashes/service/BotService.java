package com.bot.eyelashes.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@RequiredArgsConstructor
public class BotService extends TelegramLongPollingBot {


//    @Value("${telegram.name}")
    private String NAME = "@BeautyEyelashesBot";

//    @Value("${telegram.token}")
    private final String TOKEN = "5531596418:AAFw9TtWSvIgZSD_-KpRi0JUDz1LWXmfQSY";


    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
       if (update.hasMessage() && update.getMessage().hasText()){
            execute(SendMessage.builder().chatId(message.getChatId().toString()).text("You sent \n \n" + message.getText()).build());
       }
    }

    @Override
    public String getBotUsername() {
        return NAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }

    @SneakyThrows
    public static void main(String[] args) {
        BotService botService = new BotService();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(botService);

    }

}
