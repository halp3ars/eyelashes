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
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        List<InlineKeyboardButton> row3 = new ArrayList<>();
        List<String> trueDays = scheduleClientMap.getTrueDays();
        trueDays.sort(Comparator.comparing(day -> List.of("Понедельник","Вторник","Среда", "Четверг","Пятница","Суббота","Воскресенье").indexOf(day)));
        for (int days = 0; days < trueDays.size(); days++) {
            if (days < 3) {
                row1.add(InlineKeyboardButton.builder()
                        .text(trueDays.get(days))
                        .callbackData("DATE/" + trueDays
                                .get(days))
                        .build());
            }
            if (days > 2 & days < 5 ) {
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
