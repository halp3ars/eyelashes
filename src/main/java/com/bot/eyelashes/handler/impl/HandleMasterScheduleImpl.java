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
        scheduleMasterMap.getMap().forEach((k, v) -> buttons.add(List.of(InlineKeyboardButton.builder().text(v.toString()).callbackData("MASTER_ACTIVITY/" + k.toString()).build())));
        buttons.add(List.of(InlineKeyboardButton.builder().text("Готов").callbackData("MASTER_TIME").build()));
        inlineKeyboardMarkup.setKeyboard(buttons);
        return inlineKeyboardMarkup;
    }


}
