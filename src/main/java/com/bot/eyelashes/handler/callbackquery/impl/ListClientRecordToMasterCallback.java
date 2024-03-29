package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.enums.DayOfWeek;
import com.bot.eyelashes.enums.map.ScheduleMasterMap;
import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.model.entity.RecordToMaster;
import com.bot.eyelashes.repository.RecordToMasterRepository;
import com.bot.eyelashes.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.*;

@Service("DayForCheckClientRecordToMaster")
@RequiredArgsConstructor
public class ListClientRecordToMasterCallback implements Callback {
    private final RecordToMasterRepository recordToMasterRepository;
    private final MessageService messageService;

    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        List<List<InlineKeyboardButton>> buttonEmptyDayRecord = new ArrayList<>();
        List<RecordToMaster> days = recordToMasterRepository.findDayByMasterId(callbackQuery.getMessage()
                .getChatId());
        List<String> sortedDay = sortRecordsByDay(days);
        if (days.size() == 0) {
            buttonEmptyDayRecord.add(Collections.singletonList(
                    InlineKeyboardButton.builder()
                            .text("Меню")
                            .callbackData("MASTER")
                            .build()
            ));
            InlineKeyboardMarkup keyboard = InlineKeyboardMarkup.builder()
                    .keyboard(buttonEmptyDayRecord)
                    .build();

            return messageService.getReplyMessageWithKeyboard(callbackQuery.getMessage().getChatId(),
                    "Записей на вас не найдено.\nВернитесь на главное меню", keyboard);
        }

        return messageService.getReplyMessageWithKeyboard(callbackQuery.getMessage().getChatId(),
                "Выберите день", generateKeyboardWithDay(sortedDay));
    }

    private List<String> sortRecordsByDay(List<RecordToMaster> records) {
        List<String> days = new ArrayList<>();
        for (RecordToMaster record : records) {
            days.add(record.getDay());
        }

        days.sort(Comparator.comparing(day -> List.of(DayOfWeek.MONDAY.getNameDay(), DayOfWeek.TUESDAY.getNameDay(),
                DayOfWeek.WEDNESDAY.getNameDay(), DayOfWeek.THURSDAY.getNameDay(), DayOfWeek.FRIDAY.getNameDay(),
                DayOfWeek.SATURDAY.getNameDay(), DayOfWeek.SUNDAY.getNameDay()).indexOf(day)));

        return days;
    }

    private InlineKeyboardMarkup generateKeyboardWithDay(List<String> daysRecordToMaster) {
        List<InlineKeyboardButton> rowMain = new ArrayList<>();
        List<InlineKeyboardButton> rowSecond = new ArrayList<>();
        List<InlineKeyboardButton> rowThird = new ArrayList<>();

        ScheduleMasterMap scheduleMasterMap = new ScheduleMasterMap();

        for (String day : daysRecordToMaster) {
            String callbackDay = scheduleMasterMap.getKey(day);
            if (day.equals(DayOfWeek.MONDAY.getNameDay()) || day.equals(DayOfWeek.TUESDAY.getNameDay()) ||
                    day.equals(DayOfWeek.WEDNESDAY.getNameDay())) {
                rowMain.add(
                        InlineKeyboardButton.builder()
                                .text(day)
                                .callbackData("DAY_CLIENT_RECORD_TO_MASTER/" + callbackDay)
                                .build()
                );
            }

            if (day.equals(DayOfWeek.THURSDAY.getNameDay()) || day.equals(DayOfWeek.FRIDAY.getNameDay()) ||
                    day.equals(DayOfWeek.SUNDAY.getNameDay())) {
                rowSecond.add(InlineKeyboardButton.builder()
                        .text(day)
                        .callbackData("DAY_CLIENT_RECORD_TO_MASTER/" + callbackDay)
                        .build()
                );
            }

            if (day.equals(DayOfWeek.SUNDAY.getNameDay())) {
                rowThird.add(InlineKeyboardButton.builder()
                        .text(day)
                        .callbackData("DAY_CLIENT_RECORD_TO_MASTER/" + callbackDay)
                        .build()
                );
            }
        }

        rowThird.add(InlineKeyboardButton.builder()
                .text("Меню")
                .callbackData("MASTER")
                .build());

        return InlineKeyboardMarkup.builder()
                .keyboardRow(rowMain)
                .keyboardRow(rowSecond)
                .keyboardRow(rowThird)
                .build();
    }
}
