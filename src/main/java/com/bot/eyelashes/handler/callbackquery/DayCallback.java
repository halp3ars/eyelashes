package com.bot.eyelashes.handler.callbackquery;


import com.bot.eyelashes.cache.MasterDataCache;
import com.bot.eyelashes.enums.BotState;
import com.bot.eyelashes.enums.DayOfWeek;
import com.bot.eyelashes.enums.map.HashMapDayOfWeekModeService;
import com.bot.eyelashes.handler.BotStateContext;
import com.bot.eyelashes.model.dto.ScheduleDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppData;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DayCallback {
    private final HashMapDayOfWeekModeService dayOfWeekModeService;
    private final MasterDataCache masterDataCache;
    private final BotStateContext botStateContext;
    private Message message;
    private List<DayOfWeek> listDay = new ArrayList<>();

    public BotApiMethod<? extends Serializable> processInputMessage(Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();

        String days = update.getCallbackQuery()
                .getData()
                .split("/")[1];
        ScheduleDto userScheduleData = masterDataCache.getUserScheduleData(chatId);
        setCurrentDay(days, userScheduleData);
        masterDataCache.saveUserScheduleData(chatId, userScheduleData);
        if (days.equals("MASTER_TIME")) {
            BotState botState = BotState.ASK_TIME_FROM;
            return botStateContext.processInputMessage(botState, update.getCallbackQuery().getMessage());
        }

        return generateKeyboardWithText(chatId, update);
    }

    public EditMessageReplyMarkup generateKeyboardWithText(long chatId, Update update) {
        List<InlineKeyboardButton> rowMain = new ArrayList<>();
        List<InlineKeyboardButton> rowSecond = new ArrayList<>();
        List<InlineKeyboardButton> rowThird = new ArrayList<>();
        ScheduleDto userScheduleData = masterDataCache.getUserScheduleData(chatId);
        masterDataCache.saveUserScheduleData(chatId, userScheduleData);

        DayOfWeek newDayOfWeek = DayOfWeek.valueOf(update.getCallbackQuery().getData().split("/")[1]);
        listDay.add(newDayOfWeek);
        dayOfWeekModeService.setOriginalDay(chatId, newDayOfWeek);
        DayOfWeek originalDay = dayOfWeekModeService.getOriginalDay(chatId);


        for (DayOfWeek day : DayOfWeek.values()) {
            if (day.equals(DayOfWeek.MONDAY) || day.equals(DayOfWeek.TUESDAY) ||
                    day.equals(DayOfWeek.WEDNESDAY)) {
                rowMain.add(
                        InlineKeyboardButton.builder()
                                .text(getDayButton(originalDay.getNameDay(), day.getNameDay()))
                                .callbackData("MASTER_DAY/" + DayOfWeek.valueOf(day.name()))
                                .build()
                );
            } else if (day.equals(DayOfWeek.THURSDAY) || day.equals(DayOfWeek.FRIDAY) ||
                    day.equals(DayOfWeek.SATURDAY)) {
                rowSecond.add(
                        InlineKeyboardButton.builder()
                                .text(getDayButton(originalDay.getNameDay(), day.getNameDay()))
                                .callbackData("MASTER_DAY/" + DayOfWeek.valueOf(day.name()))
                                .build()
                );
            } else if (day.equals(DayOfWeek.SUNDAY) || day.equals(DayOfWeek.MASTER_TIME)) {
                rowThird.add(
                        InlineKeyboardButton.builder()
                                .text(getDayButton(originalDay.getNameDay(), day.getNameDay()))
                                .callbackData("MASTER_DAY/" + DayOfWeek.valueOf(day.name()))
                                .build()
                );
            }
        }

        InlineKeyboardMarkup keyboard = InlineKeyboardMarkup.builder()
                .keyboardRow(rowMain)
                .keyboardRow(rowSecond)
                .keyboardRow(rowThird)
                .build();

        return EditMessageReplyMarkup.builder()
                .replyMarkup(keyboard)
                .messageId(update.getCallbackQuery().getMessage().getMessageId())
                .chatId(String.valueOf(chatId))
                .build();
    }

    private String getDayButton(String saved, String current) {
        return saved.equals(current) ? current + "✅" : current;
    }

    private void setLastMessage(Message message) {
        this.message = message;
    }

    private Message getLastMessage() {
        return message;
    }

    private void setCurrentDay(String day, ScheduleDto userScheduleData) {
        if (day.equals("MONDAY")) userScheduleData.setMonday(true);
        if (day.equals("TUESDAY")) userScheduleData.setTuesday(true);
        if (day.equals("WEDNESDAY")) userScheduleData.setWednesday(true);
        if (day.equals("THURSDAY")) userScheduleData.setThursday(true);
        if (day.equals("FRIDAY")) userScheduleData.setFriday(true);
        if (day.equals("SATURDAY")) userScheduleData.setSaturday(true);
        if (day.equals("SUNDAY")) userScheduleData.setSunday(true);
        log.info("master set day = " + day);
    }
}
