package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.handler.impl.HandleMainMenuImpl;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public class CallBackMenuImpl implements Callback {
    @Override
    public SendMessage getMessageByCallback(CallbackQuery callbackQuery) {
        return SendMessage.builder()
                .replyMarkup(getMarkup())
                .chatId(callbackQuery.getMessage()
                        .getChatId()
                        .toString())
                .text("Меню")
                .build();
    }

    @Override
    public InlineKeyboardMarkup getMarkup() {
        HandleMainMenuImpl handleMainMenu = new HandleMainMenuImpl();
        return handleMainMenu.createInlineKeyboard();
    }
}
