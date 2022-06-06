package com.bot.eyelashes.handler.impl;

import com.bot.eyelashes.handler.Handle;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class HandleInfoImpl implements Handle {

    @Override
    public SendMessage getMessage(Message message) {
        return SendMessage.builder()
                .chatId(message.getChatId()
                        .toString())
                .text("НАШ БОТ ЛУЧШИЙ БОТ НА ПЛАНЕТ ЗЕМЛЯ ИГОРЬ ЛОХ ЧТО ПРОЦЕНТВО")
                .replyMarkup(InlineKeyboardMarkup.builder()
                        .keyboard(createInlineKeyboard().getKeyboard())
                        .build())
                .build();

    }

    @Override
    public InlineKeyboardMarkup createInlineKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(Arrays.asList(
                InlineKeyboardButton.builder()
                        .text("Меню")
                        .callbackData("MENU")
                        .build()));
        inlineKeyboardMarkup.setKeyboard(buttons);
        return inlineKeyboardMarkup;
    }

}
