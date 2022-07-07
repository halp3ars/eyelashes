package com.bot.eyelashes.handler.impl;

import com.bot.eyelashes.cache.MasterDataCache;
import com.bot.eyelashes.enums.map.ScheduleMasterMap;
import com.bot.eyelashes.handler.Handle;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class HandleMasterScheduleImpl implements Handle {
    private final MasterDataCache masterDataCache;

    @Override
    public SendMessage getMessage(Update update) {
        return null;
    }

    @Override
    public InlineKeyboardMarkup createInlineKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        ScheduleMasterMap scheduleMasterMap = new ScheduleMasterMap();
        Map map = scheduleMasterMap.getMap();

        buttons.add(List.of(
                InlineKeyboardButton.builder()
                        .text("Понедельник")
                        .callbackData("MASTER_ACTIVITY/MONDAY")
                        .build(),
                InlineKeyboardButton.builder()
                        .text("Вторник")
                        .callbackData("MASTER_ACTIVITY/TUESDAY")
                        .build(),
                InlineKeyboardButton.builder()
                        .text("Среда")
                        .callbackData("MASTER_ACTIVITY/WEDNESDAY")
                        .build()
        ));

        buttons.add(List.of(
                InlineKeyboardButton.builder()
                        .text("Четверг")
                        .callbackData("MASTER_ACTIVITY/THURSDAY")
                        .build(),
                InlineKeyboardButton.builder()
                        .text("Пятница")
                        .callbackData("MASTER_ACTIVITY/FRIDAY")
                        .build(),
                InlineKeyboardButton.builder()
                        .text("Суббота")
                        .callbackData("MASTER_ACTIVITY/SATURDAY")
                        .build()
        ));

        buttons.add(List.of(
                InlineKeyboardButton.builder()
                        .text("Воскресенье")
                        .callbackData("MASTER_ACTIVITY/SUNDAY")
                        .build(),
                InlineKeyboardButton.builder()
                        .text("Готов")
                        .callbackData("MASTER_TIME")
                        .build()));
        inlineKeyboardMarkup.setKeyboard(buttons);
        return inlineKeyboardMarkup;
    }
}
