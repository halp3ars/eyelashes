package com.bot.eyelashes.handler.impl;

import com.bot.eyelashes.enums.DayOfWeek;
import com.bot.eyelashes.handler.Handle;
import com.bot.eyelashes.service.HashMapDayOfWeekModeService;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class HandleMasterScheduleImpl implements Handle {
    private final HashMapDayOfWeekModeService dayOfWeekModeService;

    @Override
    public SendMessage getMessage(Update update) {
        return null;
    }

    @Override
    public InlineKeyboardMarkup createInlineKeyboard() {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

        buttons.add(List.of(
                InlineKeyboardButton.builder()
                                    .text(DayOfWeek.MONDAY.name())
                                    .callbackData("MASTER_DAY/" + DayOfWeek.MONDAY)
                                    .build(),
                InlineKeyboardButton.builder()
                                    .text(DayOfWeek.TUESDAY.name())
                                    .callbackData("MASTER_DAY/" + DayOfWeek.TUESDAY)
                                    .build(),
                InlineKeyboardButton.builder()
                                    .text(DayOfWeek.WEDNESDAY.name())
                                    .callbackData("MASTER_DAY/" + DayOfWeek.WEDNESDAY)
                                    .build()
        ));

        buttons.add(List.of(
                InlineKeyboardButton.builder()
                                    .text(DayOfWeek.THURSDAY.name())
                                    .callbackData("MASTER_DAY/" + DayOfWeek.THURSDAY)
                                    .build(),
                InlineKeyboardButton.builder()
                                    .text(DayOfWeek.FRIDAY.name())
                                    .callbackData("MASTER_DAY/" + DayOfWeek.FRIDAY)
                                    .build(),
                InlineKeyboardButton.builder()
                                    .text(DayOfWeek.SATURDAY.name())
                                    .callbackData("MASTER_DAY/" + DayOfWeek.SATURDAY)
                                    .build()
        ));

        buttons.add(List.of(
                InlineKeyboardButton.builder()
                                    .text(DayOfWeek.SUNDAY.name())
                                    .callbackData("MASTER_ACTIVITY/" + DayOfWeek.SUNDAY)
                                    .build(),
                InlineKeyboardButton.builder()
                                    .text("Готов")
                                    .callbackData("MASTER_TIME")
                                    .build()));

        return InlineKeyboardMarkup.builder().keyboard(buttons).build();
    }

    public InlineKeyboardMarkup generateKeyboardWithText(long chatId) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        DayOfWeek originalDay = dayOfWeekModeService.getOriginalDay(chatId);
        DayOfWeek targetDay = dayOfWeekModeService.getTargetDay(chatId);

        for (DayOfWeek day : DayOfWeek.values()) {
            buttons.add(List.of(
                    InlineKeyboardButton.builder()
                                        .text(getDayButton(targetDay.name(),day.name()))
                                        .callbackData("MASTER_DAY/" + DayOfWeek.valueOf(day.name()))
                                        .build()
            ));
        }

        return InlineKeyboardMarkup.builder().keyboard(buttons).build();
    }

    private String getDayButton(String saved, String current) {
        return saved == current ? current + "✅" : current;
    }
}
