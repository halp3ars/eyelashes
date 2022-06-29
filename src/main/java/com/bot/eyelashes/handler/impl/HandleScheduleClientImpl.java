package com.bot.eyelashes.handler.impl;

import com.bot.eyelashes.handler.Handle;
import com.bot.eyelashes.handler.registration.ScheduleForClient;
import com.bot.eyelashes.handler.schedule.HandleSchedule;
import com.bot.eyelashes.mapper.ScheduleMapper;
import com.bot.eyelashes.model.dto.ScheduleDto;
import com.bot.eyelashes.model.entity.Schedule;
import com.bot.eyelashes.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
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
        return null;
    }

    public InlineKeyboardMarkup createInlineKeyboardWithCallback(CallbackQuery callbackQuery){
        ScheduleForClient scheduleForClient = new ScheduleForClient(scheduleRepository,scheduleMapper);
        ScheduleDto scheduleDto = scheduleForClient.getMasterDays(649681305L);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(Arrays.asList(
                InlineKeyboardButton.builder().text(scheduleDto.getMasterId().toString()).callbackData("asd").build()
        ));
        inlineKeyboardMarkup.setKeyboard(buttons);
        return inlineKeyboardMarkup;
    }


    private List<String> getDayName(ScheduleDto scheduleDto){
        List<String> days = new ArrayList<>();
        days.addAll(List.of("Понедельник","Вторник","Среда","Четверг","Пятница"));
        return null;
    }
}
