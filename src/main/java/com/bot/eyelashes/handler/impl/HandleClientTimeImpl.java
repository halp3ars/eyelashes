package com.bot.eyelashes.handler.impl;


import com.bot.eyelashes.enums.map.ScheduleMap;
import com.bot.eyelashes.handler.Handle;
import com.bot.eyelashes.handler.registration.ScheduleForClient;
import com.bot.eyelashes.handler.registration.TimeForClient;
import com.bot.eyelashes.model.dto.ScheduleDto;
import com.bot.eyelashes.repository.RecordToMasterRepository;
import com.bot.eyelashes.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


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
        TimeForClient timeForClient = new TimeForClient(recordToMasterRepository,scheduleRepository);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        timeForClient.getWorkTime(HandleRecordMenuImpl.masterId).forEach(time -> buttons.add(List.of(InlineKeyboardButton.builder().text(time.toString() + ":00").callbackData("FILLED/" + time).build())));
        inlineKeyboardMarkup.setKeyboard(buttons);
        return inlineKeyboardMarkup;

    }
}
