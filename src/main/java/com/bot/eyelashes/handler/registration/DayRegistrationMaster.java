package com.bot.eyelashes.handler.registration;

import com.bot.eyelashes.cache.MasterDataCache;
import com.bot.eyelashes.enums.BotState;
import com.bot.eyelashes.enums.DayOfWeek;
import com.bot.eyelashes.enums.map.HashMapDayOfWeekModeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DayRegistrationMaster {
    private final HashMapDayOfWeekModeService dayOfWeekModeService;
    private final MasterDataCache masterDataCache;

    public EditMessageReplyMarkup getMessage(Message message) {
        if (message.getContact().getPhoneNumber() == null) {
            masterDataCache.setUsersCurrentBotState(message.getChatId(), BotState.ASK_DAY);
        }
        return generateKeyboardWithText1(message.getChatId());
    }

    public EditMessageReplyMarkup generateKeyboardWithText1(long chatId) {
        List<InlineKeyboardButton> rowMain = new ArrayList<>();
        List<InlineKeyboardButton> rowSecond = new ArrayList<>();
        List<InlineKeyboardButton> rowThird = new ArrayList<>();

        DayOfWeek targetDay = dayOfWeekModeService.getTargetDay(chatId);

        for (DayOfWeek day : DayOfWeek.values()) {
            if (!day.equals(DayOfWeek.NONE)) {
                if (day.equals(DayOfWeek.MONDAY) || day.equals(DayOfWeek.TUESDAY) || day.equals(DayOfWeek.WEDNESDAY)) {
                    rowMain.add(
                            InlineKeyboardButton.builder()
                                    .text(getDayButton(targetDay.getNameDay(), day.getNameDay()))
                                    .callbackData("MASTER_DAY/" + DayOfWeek.valueOf(day.name()))
                                    .build()
                    );
                } else if (day.equals(DayOfWeek.THURSDAY) || day.equals(DayOfWeek.FRIDAY) ||
                        day.equals(DayOfWeek.SATURDAY)) {
                    rowSecond.add(
                            InlineKeyboardButton.builder()
                                    .text(getDayButton(targetDay.getNameDay(), day.getNameDay()))
                                    .callbackData("MASTER_DAY/" + DayOfWeek.valueOf(day.name()))
                                    .build()
                    );
                } else {
                    rowThird.add(
                            InlineKeyboardButton.builder()
                                    .text(getDayButton(targetDay.getNameDay(), day.getNameDay()))
                                    .callbackData("MASTER_DAY/" + DayOfWeek.valueOf(day.name()))
                                    .build()
                    );
                }
            }
        }
        InlineKeyboardMarkup keyboard = InlineKeyboardMarkup.builder()
                .keyboardRow(rowMain)
                .keyboardRow(rowSecond)
                .keyboardRow(rowThird)
                .build();

        return EditMessageReplyMarkup.builder().replyMarkup(keyboard).build();
    }

    private String getDayButton(String saved, String current) {
        return saved.equals(current) ? current + "✅" : current;
    }
}
