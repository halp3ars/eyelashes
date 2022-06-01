package com.bot.eyelashes.handler;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class SystemHandler {

    public SendMessage getMessageInfo(String chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        StringBuilder text = new StringBuilder();
        text.append("Бот позволяет записать на услуги");
        sendMessage.setText(text.toString());
        return sendMessage;
    }
}
