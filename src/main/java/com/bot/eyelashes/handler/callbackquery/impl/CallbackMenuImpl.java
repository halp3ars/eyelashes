package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.handler.impl.HandleMainMenuImpl;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public class CallbackMenuImpl implements Callback {
    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        HandleMainMenuImpl handleMainMenu = new HandleMainMenuImpl();

        return SendMessage.builder()
                .replyMarkup(handleMainMenu.createInlineKeyboard())
                .chatId(callbackQuery.getMessage()
                        .getChatId()
                        .toString())
                .text("Меню")
                .build();
    }
}
