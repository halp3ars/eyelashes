package com.bot.eyelashes.handler.impl;

import com.bot.eyelashes.handler.Handle;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class HandleMasterImpl implements Handle {
    @Override
    public SendMessage getMessage(Message message) {
        return null;
    }


    @Override
    public InlineKeyboardMarkup createInlineKeyboard() {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> rowDataUser = new ArrayList<>();


        rowDataUser.add(InlineKeyboardButton.builder()
                .text("ФИО")
                .callbackData("FULL_NAME")
                .build());

        buttons.add(Arrays.asList(
                        InlineKeyboardButton.builder()
                                .text("Контакт")
                                .callbackData("CONTACT")
                                .build(),
                        InlineKeyboardButton.builder()
                                .callbackData("ACTIVITY")
                                .text("Услуги")
                                .build()
                )
        );

        return InlineKeyboardMarkup.builder()
                .keyboardRow(rowDataUser)
                .keyboard(buttons)
                .build();
    }
}
