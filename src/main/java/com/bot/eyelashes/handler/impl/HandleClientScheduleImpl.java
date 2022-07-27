package com.bot.eyelashes.handler.impl;

import com.bot.eyelashes.cache.ClientDataCache;
import com.bot.eyelashes.handler.Handle;
import com.bot.eyelashes.model.entity.PeriodOfWork;
import com.bot.eyelashes.model.entity.Schedule2;
import com.bot.eyelashes.repository.Schedule2Repository;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
public class HandleClientScheduleImpl implements Handle {



    private final Schedule2Repository schedule2Repository;
    private final ClientDataCache clientDataCache;
    private final List<String> DAYS = List.of("Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота", "Воскресенье");

    @Override
    public SendMessage getMessage(Update update) {
        return null;
    }

    @Override
    public InlineKeyboardMarkup createInlineKeyboard() {
        return null;
    }

    public InlineKeyboardMarkup createInlineKeyboard(Long chatId) {
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        List<InlineKeyboardButton> row3 = new ArrayList<>();
        clientDataCache.getRecordData(chatId).getMasterId();
        Schedule2 schedule = schedule2Repository.findByTelegramId(clientDataCache.getRecordData(chatId).getMasterId());
        List<PeriodOfWork> periodOfWorks = schedule.getPeriodOfWorks();
        List<String> trueDays = periodOfWorks.stream()
                .map(PeriodOfWork::getDay)
                .sorted(Comparator.comparing(DAYS::indexOf))
                .toList();
        for (int days = 0; days < trueDays.size(); days++) {
            if (days < 3) {
                row1.add(InlineKeyboardButton.builder()
                        .text(trueDays.get(days))
                        .callbackData("DATE/" + trueDays
                                .get(days))
                        .build());
            }
            if (days > 2 & days < 5) {
                row2.add(InlineKeyboardButton.builder()
                        .text(trueDays.get(days))
                        .callbackData("DATE/" + trueDays
                                .get(days))
                        .build());
            }
            if (days > 4 & days < 7) {
                row3.add(InlineKeyboardButton.builder()
                        .text(trueDays.get(days))
                        .callbackData("DATE/" + trueDays
                                .get(days))
                        .build());
            }
        }
        return InlineKeyboardMarkup.builder()
                .keyboardRow(row1)
                .keyboardRow(row2)
                .keyboardRow(row3)
                .build();
    }

}
