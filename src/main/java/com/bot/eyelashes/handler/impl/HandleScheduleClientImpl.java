package com.bot.eyelashes.handler.impl;

import com.bot.eyelashes.enums.map.ScheduleMap;
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
import java.util.List;

@RequiredArgsConstructor
public class HandleScheduleClientImpl implements Handle {


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
        ScheduleMap scheduleMap = new ScheduleMap(scheduleDto);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        scheduleMap.getScheduleDayMap().forEach(days -> buttons.add(List.of(InlineKeyboardButton.builder().text(days.toString()).callbackData("TIME/" + days).build())));
        inlineKeyboardMarkup.setKeyboard(buttons);
        return inlineKeyboardMarkup;
    }

}
