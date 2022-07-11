package com.bot.eyelashes.handler.impl;

import com.bot.eyelashes.enums.map.ScheduleClientMap;
import com.bot.eyelashes.handler.Handle;
import com.bot.eyelashes.handler.registration.ScheduleForClient;
import com.bot.eyelashes.mapper.ScheduleMapper;
import com.bot.eyelashes.model.dto.ScheduleDto;
import com.bot.eyelashes.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class HandleClientScheduleImpl implements Handle {


    private final ScheduleMapper scheduleMapper;

    private final ScheduleRepository scheduleRepository;

    @Override
    public SendMessage getMessage(Update update) {
        return null;
    }

    @Override
    public InlineKeyboardMarkup createInlineKeyboard() {
        ScheduleForClient scheduleForClient = new ScheduleForClient(scheduleRepository, scheduleMapper);
        ScheduleDto scheduleDto = scheduleForClient.getMasterDays(HandleRecordMenuImpl.masterId);
        ScheduleClientMap scheduleClientMap = new ScheduleClientMap(scheduleDto);
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        List<String> trueDays = scheduleClientMap.getTrueDays();
        for (int days = 0 ; days < trueDays.size(); days++) {
            if (days % 2 == 0) {
                row1.add(InlineKeyboardButton.builder()
                        .text(scheduleClientMap.getTrueDays()
                                .get(days))
                        .callbackData("DATE/" + scheduleClientMap.getTrueDays()
                                .get(days))
                        .build());
            }
            if (days % 2 != 0) {
                row2.add(InlineKeyboardButton.builder()
                        .text(scheduleClientMap.getTrueDays()
                                .get(days))
                        .callbackData("DATE/" + scheduleClientMap.getTrueDays()
                                .get(days))
                        .build());
            }
        }
        return InlineKeyboardMarkup.builder()
                .keyboardRow(row1)
                .keyboardRow(row2)
                .build();
    }

}
