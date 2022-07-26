package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.repository.MasterRepository;
import com.bot.eyelashes.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service("AnswerDeleteProfileMasterCallbackImpl")
@RequiredArgsConstructor
public class AnswerDeleteProfileMasterCallbackImpl implements Callback {
    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();

        return SendMessage.builder()
                .chatId(chatId)
                .text("Вы уверены, что хотите удалить профиль?")
                .replyMarkup(answerButtons())
                .build();
    }

    private InlineKeyboardMarkup answerButtons() {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(List.of(
                InlineKeyboardButton.builder()
                        .text("Да")
                        .callbackData("DELETE_MASTER")
                        .build(),
                InlineKeyboardButton.builder()
                        .text("Нет")
                        .callbackData("MASTER")
                        .build()
        ));

        return InlineKeyboardMarkup.builder()
                .keyboard(buttons)
                .build();
    }

}
