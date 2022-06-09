package com.bot.eyelashes.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public class MessageService {
    public SendMessage getReplyMessage(Long chatId, String text) {
        return SendMessage.builder().text(text).chatId(chatId.toString()).build();
    }
}
