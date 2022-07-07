package com.bot.eyelashes.handler.impl;

import com.bot.eyelashes.cache.MasterDataCache;
import com.bot.eyelashes.handler.Handle;
import com.bot.eyelashes.handler.callbackquery.impl.CallbackMasterTimeToImpl;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
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
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<Integer> workHours = new ArrayList<>();

        List<InlineKeyboardButton> rowMain = new ArrayList<>();
        List<InlineKeyboardButton> rowSecond = new ArrayList<>();
        List<InlineKeyboardButton> rowThird = new ArrayList<>();
        for (int i = CallbackMasterTimeToImpl.time + 1; i < 21; i++) {
            if (i <= CallbackMasterTimeToImpl.time + 3) {
                rowMain.add(InlineKeyboardButton.builder()
                        .text(i + ": 00")
                        .callbackData("REGISTERED/" + i)
                        .build());
            } else if (i > CallbackMasterTimeToImpl.time + 3 && i <= CallbackMasterTimeToImpl.time + 6) {
                rowSecond.add(InlineKeyboardButton.builder()
                        .text(i + ": 00")
                        .callbackData("REGISTERED/" + i)
                        .build());
            } else if (i > CallbackMasterTimeToImpl.time + 6) {
                rowThird.add(InlineKeyboardButton.builder()
                        .text(i + ": 00")
                        .callbackData("REGISTERED/" + i)
                        .build());
            }

        }
        IntStream.range(CallbackMasterTimeToImpl.time + 1, 22)
                .forEach(workHours::add);
        workHours.forEach(timeText -> buttons.add(List.of(InlineKeyboardButton.builder()
                .text(timeText.toString())
                .callbackData("REGISTERED/" + timeText)
                .build())));
        return InlineKeyboardMarkup.builder()
                .keyboardRow(rowMain)
                .keyboardRow(rowSecond)
                .keyboardRow(rowThird)
                .build();
    }

}
