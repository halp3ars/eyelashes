package com.bot.eyelashes.handler.impl;

import com.bot.eyelashes.enums.CallbackQueryType;
import com.bot.eyelashes.handler.Handle;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HandleMainMenuImpl implements Handle {


    @Override
    public SendMessage getMessage(Update update) {
        return SendMessage.builder()
                .replyMarkup(createInlineKeyboard())
                .chatId(update.getMessage()
                        .getChatId()
                        .toString())
                .text("Меншечька")
                .build();
    }

    private InlineKeyboardMarkup createInlineKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(Arrays.asList(
                InlineKeyboardButton.builder()
                        .text("Клиент")
                        .callbackData(CallbackQueryType.CLIENT.name())
                        .build(),
                InlineKeyboardButton.builder()
                        .text("Мастер")
                        .callbackData(CallbackQueryType.MASTER.name())
                        .build()));
        inlineKeyboardMarkup.setKeyboard(buttons);
        return inlineKeyboardMarkup;
    }
}
