package com.bot.eyelashes.service;

import com.bot.eyelashes.config.properties.TelegramProperties;
import com.bot.eyelashes.handler.Handle;
import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.handler.impl.HandleClientImpl;
import com.bot.eyelashes.map.CallBackQueryTypeMap;
import com.bot.eyelashes.map.CommandMap;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@Slf4j
@RequiredArgsConstructor
public class Bot extends TelegramLongPollingBot {

    private final TelegramProperties telegramProperties;

    private final CallBackQueryTypeMap callBackQueryTypeMap;

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if(update.hasCallbackQuery()){
            Callback callback = callBackQueryTypeMap.getCallback(update.getCallbackQuery().getData().split("/")[0]);
            execute(callback.getMessageByCallback(update.getCallbackQuery()));
        }else if(message.hasText()){
            CommandMap commandMap = new CommandMap();
            Handle handle = commandMap.getCommand(message.getText());
            execute(handle.getMessage(update));
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
