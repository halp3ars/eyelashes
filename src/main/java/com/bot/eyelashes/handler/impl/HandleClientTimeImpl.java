package com.bot.eyelashes.handler.impl;


import com.bot.eyelashes.handler.Handle;
import com.bot.eyelashes.handler.registration.TimeForClient;
import com.bot.eyelashes.repository.RecordToMasterRepository;
import com.bot.eyelashes.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
public class HandleClientTimeImpl implements Handle {

    private final RecordToMasterRepository recordToMasterRepository;

    private final ScheduleRepository scheduleRepository;

    @Override
    public SendMessage getMessage(Update update) {
        return null;
    }

    @Override
    public InlineKeyboardMarkup createInlineKeyboard() {
        TimeForClient timeForClient = new TimeForClient(recordToMasterRepository, scheduleRepository);
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        List<InlineKeyboardButton> row3 = new ArrayList<>();
        List<InlineKeyboardButton> row4 = new ArrayList<>();
        List<Integer> workTime = timeForClient.getWorkTime(HandleRecordMenuImpl.masterId);
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
