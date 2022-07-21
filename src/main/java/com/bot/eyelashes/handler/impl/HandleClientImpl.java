package com.bot.eyelashes.handler.impl;

import com.bot.eyelashes.enums.map.TypeOfActivity;
import com.bot.eyelashes.handler.Handle;
import com.bot.eyelashes.repository.MasterRepository;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class HandleClientImpl implements Handle {


    private final MasterRepository masterRepository;


    @Override
    public SendMessage getMessage(Update update) {
        return SendMessage.builder()
                .chatId(update.getMessage()
                        .getChatId()
                        .toString())
                .replyMarkup(createInlineKeyboard())
                .text("Выберите вид деятельности")
                .build();
    }

    @Override
    public InlineKeyboardMarkup createInlineKeyboard() {
        return null;
    }


    public InlineKeyboardMarkup createInlineKeyboard(CallbackQuery callbackQuery) {
        TypeOfActivity typeOfActivity = new TypeOfActivity();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        if (!masterRepository.findByActivity("Брови")
                .isEmpty()) {
            row1.add(InlineKeyboardButton.builder()
                    .text(typeOfActivity.getCommand("EYEBROWS"))
                    .callbackData("EYEBROWS")
                    .build());
        }
        if (!masterRepository.findByActivity("Ресницы")
                .isEmpty()) {
            row1.add(InlineKeyboardButton.builder()
                    .text(typeOfActivity.getCommand("EYELASHES"))
                    .callbackData("EYELASHES")
                    .build());
        }
        if (!masterRepository.findByActivity("Ногти")
                .isEmpty()) {
            row1.add(InlineKeyboardButton.builder()
                    .text(typeOfActivity.getCommand("NAILS"))
                    .callbackData("NAILS")
                    .build());
        }
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        row2.add(InlineKeyboardButton.builder()
                .text("Мои записи")
                .callbackData("ALL_RECORDS")
                .build());
        return InlineKeyboardMarkup.builder()
                .keyboardRow(row1)
                .keyboardRow(row2)
                .build();
    }
}
