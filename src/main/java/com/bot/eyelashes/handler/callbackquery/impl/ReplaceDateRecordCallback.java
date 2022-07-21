package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.handler.callbackquery.Callback;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReplaceDateRecordCallback implements Callback {
    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        long clientIdInCallback = Integer.parseInt(callbackQuery.getData().split("/")[1]);
        return SendMessage.builder()
                .text("Выберите день для смены записи")
                .chatId(callbackQuery.getMessage()
                        .getChatId().toString())
                .replyMarkup(keyboardForReplaceDayRecord(clientIdInCallback))
                .build();
    }

    private InlineKeyboardMarkup keyboardForReplaceDayRecord(long clientId) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(List.of(
                InlineKeyboardButton.builder()
                        .text("Понедельник")
                        .callbackData("REPLACE_DAY_RECORD_TO_MASTER/MONDAY/" + clientId)
                        .build(),
                InlineKeyboardButton.builder()
                        .text("Вторник")
                        .callbackData("REPLACE_DAY_RECORD_TO_MASTER/TUESDAY/"+ clientId)
                        .build(),
                InlineKeyboardButton.builder()
                        .text("Среда")
                        .callbackData("REPLACE_DAY_RECORD_TO_MASTER/WEDNESDAY/"+ clientId)
                        .build()
        ));

        buttons.add(List.of(
                InlineKeyboardButton.builder()
                        .text("Четверг")
                        .callbackData("REPLACE_DAY_RECORD_TO_MASTER/THURSDAY/"+ clientId)
                        .build(),
                InlineKeyboardButton.builder()
                        .text("Пятница")
                        .callbackData("REPLACE_DAY_RECORD_TO_MASTER/FRIDAY/"+ clientId)
                        .build(),
                InlineKeyboardButton.builder()
                        .text("Суббота")
                        .callbackData("REPLACE_DAY_RECORD_TO_MASTER/SATURDAY/"+ clientId)
                        .build()

        ));

        buttons.add(List.of(
                InlineKeyboardButton.builder()
                        .text("Воскресенье")
                        .callbackData("REPLACE_DAY_RECORD_TO_MASTER/SUNDAY")
                        .build()
        ));

        return InlineKeyboardMarkup.builder()
                .keyboard(buttons)
                .build();
    }
}
