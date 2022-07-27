package com.bot.eyelashes.handler.impl;


import com.bot.eyelashes.cache.ClientDataCache;
import com.bot.eyelashes.handler.Handle;
import com.bot.eyelashes.handler.registration.TimeForClient;
import com.bot.eyelashes.model.dto.RecordToMasterDto;
import com.bot.eyelashes.model.entity.PeriodOfWork;
import com.bot.eyelashes.model.entity.Schedule2;
import com.bot.eyelashes.repository.RecordToMasterRepository;
import com.bot.eyelashes.repository.Schedule2Repository;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;


@RequiredArgsConstructor
public class HandleClientTimeImpl implements Handle {


    private final Schedule2Repository schedule2Repository;
    private final ClientDataCache clientDataCache;
    private final RecordToMasterRepository recordToMasterRepository;

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
        List<InlineKeyboardButton> row4 = new ArrayList<>();
        TimeForClient timeForClient = new TimeForClient(recordToMasterRepository,schedule2Repository,clientDataCache);
        List<Integer> workTime = timeForClient.getWorkTime(chatId);
        for (int i = 0; i < workTime.size(); i++) {
            if (workTime.get(i) >= 8 & workTime.get(i) <= 10) {
                String timeString = workTime.get(i)
                        .toString();
                row1.add(InlineKeyboardButton.builder()
                        .text(timeString + ":00")
                        .callbackData("TIME/" + timeString)
                        .build());
            } else if (workTime.get(i) >= 11 & workTime.get(i) <= 13) {
                String timeString = workTime.get(i)
                        .toString();
                row2.add(InlineKeyboardButton.builder()
                        .text(timeString + ":00")
                        .callbackData("TIME/" + timeString)
                        .build());
            } else if (workTime.get(i) >= 14 & workTime.get(i) <= 16) {
                String timeString = workTime.get(i)
                        .toString();
                row3.add(InlineKeyboardButton.builder()
                        .text(timeString + ":00")
                        .callbackData("TIME/" + timeString)
                        .build());
            } else if (workTime.get(i) >= 17 & workTime.get(i) <= 20) {
                String timeString = workTime.get(i)
                        .toString();
                row4.add(InlineKeyboardButton.builder()
                        .text(timeString + ":00")
                        .callbackData("TIME/" + timeString)
                        .build());
            }
        }
        return InlineKeyboardMarkup.builder()
                .keyboardRow(row1)
                .keyboardRow(row2)
                .keyboardRow(row3)
                .keyboardRow(row4)
                .build();
    }
}
