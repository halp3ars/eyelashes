package com.bot.eyelashes.handler.impl;

import com.bot.eyelashes.cache.MasterDataCache;
import com.bot.eyelashes.enums.map.ScheduleMasterMap;
import com.bot.eyelashes.handler.Handle;
import com.bot.eyelashes.model.dto.ScheduleDto;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class HandleScheduleMasterImpl implements Handle {

    private final MasterDataCache masterDataCache;


    @Override
    public SendMessage getMessage(Update update) {
        return null;
    }

    @Override
    public InlineKeyboardMarkup createInlineKeyboard() {
      return null;
    }

    public InlineKeyboardMarkup createInlineKeyboardWithCallback(CallbackQuery callbackQuery) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        ScheduleMasterMap scheduleMasterMap = new ScheduleMasterMap();
        ScheduleDto userScheduleData = masterDataCache.getUserScheduleData(callbackQuery.getMessage()
                .getChatId());
        String day = callbackQuery.getData()
                .split("/" )[1];
        if(day.equals("MONDAY")) userScheduleData.setMonday(true);
        if(day.equals("TUESDAY")) userScheduleData.setTuesday(true);
        if(day.equals("WEDNESDAY")) userScheduleData.setWednesday(true);
        if(day.equals("THURSDAY")) userScheduleData.setThursday(true);
        if(day.equals("FRIDAY")) userScheduleData.setFriday(true);
        userScheduleData.setTelegramId(callbackQuery.getMessage().getChatId());
        masterDataCache.saveUserScheduleData(callbackQuery.getMessage()
                .getChatId(), userScheduleData);
        scheduleMasterMap.getMap().forEach((k, v) -> buttons.add(List.of(InlineKeyboardButton.builder().text(v.toString()).callbackData("MASTER_ACTIVITY/" + k.toString()).build())));
        buttons.add(List.of(InlineKeyboardButton.builder().text("Готов").callbackData("MASTER_TIME").build()));
        inlineKeyboardMarkup.setKeyboard(buttons);
        return inlineKeyboardMarkup;
    }

}
