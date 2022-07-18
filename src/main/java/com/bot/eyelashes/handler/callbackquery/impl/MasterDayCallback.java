package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.cache.MasterDataCache;
import com.bot.eyelashes.enums.BotState;
import com.bot.eyelashes.enums.DayOfWeek;
import com.bot.eyelashes.enums.map.HashMapDayOfWeekModeService;
import com.bot.eyelashes.handler.BotStateContext;
import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.handler.impl.HandleMasterScheduleImpl;
import com.bot.eyelashes.model.dto.ScheduleDto;
import com.bot.eyelashes.validation.DayMasterValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service("MasterDayCallback")
@RequiredArgsConstructor
@Slf4j
public class MasterDayCallback implements Callback {
    private final MasterDataCache masterDataCache;
    private final BotStateContext botStateContext;
    private final HashMapDayOfWeekModeService dayOfWeekModeService;

    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {

        Long chatId = callbackQuery.getMessage()
                .getChatId();
        BotState botState = BotState.ASK_DAY;
        ScheduleDto userScheduleData = masterDataCache.getUserScheduleData(chatId);
        String day = callbackQuery.getData().split("/")[1];

        DayOfWeek newDayOfWeek = DayOfWeek.valueOf(callbackQuery.getData().split("/")[1]);
        dayOfWeekModeService.setTargetDay(chatId, newDayOfWeek);
        dayOfWeekModeService.setOriginalDay(chatId, newDayOfWeek);
        setCurrentDay(day, userScheduleData);
        generateKeyboardWithText(callbackQuery.getMessage().getChatId());



        masterDataCache.saveUserScheduleData(chatId, userScheduleData);
        if (day.equals("MASTER_TIME")) {
            if (DayMasterValidation.isEmptyDaysForMasterRegistration(userScheduleData)) {
                botState = BotState.ASK_DAY;
            } else {
                botState = BotState.ASK_TIME_FROM;
            }

        }
        masterDataCache.setUsersCurrentBotState(chatId, botState);

        return botStateContext.processInputMessage(botState, callbackQuery.getMessage());
    }

    private void setCurrentDay(String day, ScheduleDto userScheduleData) {
        if (day.equals("MONDAY")) userScheduleData.setMonday(true);
        if (day.equals("TUESDAY")) userScheduleData.setTuesday(true);
        if (day.equals("WEDNESDAY")) userScheduleData.setWednesday(true);
        if (day.equals("THURSDAY")) userScheduleData.setThursday(true);
        if (day.equals("FRIDAY")) userScheduleData.setFriday(true);
        if (day.equals("SATURDAY")) userScheduleData.setSaturday(true);
        if (day.equals("SUNDAY")) userScheduleData.setSunday(true);
    }

    public EditMessageReplyMarkup generateKeyboardWithText(long chatId) {
        List<InlineKeyboardButton> rowMain = new ArrayList<>();
        List<InlineKeyboardButton> rowSecond = new ArrayList<>();
        List<InlineKeyboardButton> rowThird = new ArrayList<>();

        DayOfWeek targetDay = dayOfWeekModeService.getTargetDay(chatId);
        DayOfWeek originalDay = dayOfWeekModeService.getOriginalDay(chatId);

        for (DayOfWeek day : DayOfWeek.values()) {
            if (!day.equals(DayOfWeek.NONE)) {
                if (day.equals(DayOfWeek.MONDAY) || day.equals(DayOfWeek.TUESDAY) || day.equals(DayOfWeek.WEDNESDAY)) {
                    rowMain.add(
                            InlineKeyboardButton.builder()
                                    .text(getDayButton(targetDay.getNameDay(), originalDay.getNameDay()))
                                    .callbackData("MASTER_DAY/" + DayOfWeek.valueOf(day.name()))
                                    .build()
                    );
                } else if (day.equals(DayOfWeek.THURSDAY) || day.equals(DayOfWeek.FRIDAY) ||
                        day.equals(DayOfWeek.SATURDAY)) {
                    rowSecond.add(
                            InlineKeyboardButton.builder()
                                    .text(getDayButton(targetDay.getNameDay(), originalDay.getNameDay()))
                                    .callbackData("MASTER_DAY/" + DayOfWeek.valueOf(day.name()))
                                    .build()
                    );
                } else {
                    rowThird.add(
                            InlineKeyboardButton.builder()
                                    .text(getDayButton(targetDay.getNameDay(), originalDay.getNameDay()))
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
        return saved.equals(current) ? current + "✅" : saved + "✅";
    }
}
