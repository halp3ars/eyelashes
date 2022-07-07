package com.bot.eyelashes.handler.impl;

import com.bot.eyelashes.cache.MasterDataCache;
import com.bot.eyelashes.handler.Handle;
import com.bot.eyelashes.handler.callbackquery.impl.CallbackMasterTimeToImpl;
import com.bot.eyelashes.model.dto.ScheduleDto;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class HandleMasterTimeToImpl implements Handle {

    private final MasterDataCache masterDataCache;

    @Override
    public SendMessage getMessage(Update update) {
        return null;
    }

    @Override
    public InlineKeyboardMarkup createInlineKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<Integer> workHours = new ArrayList<>();
        IntStream.range(CallbackMasterTimeToImpl.time + 1, 22)
                .forEach(workHours::add);
        workHours.forEach(timeText -> buttons.add(List.of(InlineKeyboardButton.builder()
                .text(timeText.toString())
                .callbackData("REGISTERED/" + timeText)
                .build())));
        inlineKeyboardMarkup.setKeyboard(buttons);
        return inlineKeyboardMarkup;
    }

}
