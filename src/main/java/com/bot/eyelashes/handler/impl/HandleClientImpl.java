package com.bot.eyelashes.handler.impl;

import com.bot.eyelashes.enums.TypeOfActivity;
import com.bot.eyelashes.handler.Handle;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HandleClientImpl implements Handle {
    @Override
    public SendMessage getMessage(Message message) {
        return SendMessage.builder()
                .chatId(message.getChatId()
                        .toString())
                .text("Выберите вид деятельности")
                .replyMarkup(createInlineKeyboard())
                .build();
    }

    @Override
    public InlineKeyboardMarkup createInlineKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(Arrays.asList(
                InlineKeyboardButton.builder()
                        .text(TypeOfActivity.EYEBROWS.getActivity())
                        .callbackData("EYEBROWS")
                        .build(),
                InlineKeyboardButton.builder()
                        .text(TypeOfActivity.EYELASH.getActivity())
                        .callbackData("EYELASH")
                        .build(),
                InlineKeyboardButton.builder()
                        .text(TypeOfActivity.NAILS.getActivity())
                        .callbackData("NAILS")
                        .build()));
        inlineKeyboardMarkup.setKeyboard(buttons);
        return inlineKeyboardMarkup;
    }
}
