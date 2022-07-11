package com.bot.eyelashes.handler.callbackquery;

import com.bot.eyelashes.cache.MasterDataCache;
import com.bot.eyelashes.enums.BotState;
import com.bot.eyelashes.enums.DayOfWeek;
import com.bot.eyelashes.handler.BotStateContext;
import com.bot.eyelashes.model.dto.ScheduleDto;
import com.bot.eyelashes.service.HashMapDayOfWeekModeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
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
        Long chatId = callbackQuery.getMessage().getChatId();
        ScheduleDto userScheduleData = masterDataCache.getUserScheduleData(chatId);
        String day = callbackQuery.getData().split("/")[1];
        DayOfWeek newDayOfWeek = DayOfWeek.valueOf(callbackQuery.getData().split("/")[1]);
        dayOfWeekModeService.setTargetDay(callbackQuery.getMessage().getChatId(), newDayOfWeek);
        setCurrentDay(day, userScheduleData);
        userScheduleData.setTelegramId(chatId);
        log.info("master id set in schedule = " + userScheduleData.getTelegramId());
        masterDataCache.saveUserScheduleData(chatId, userScheduleData);
        BotState botState = BotState.ASK_DAY;
        generateKeyboardChooseButton(callbackQuery.getMessage().getChatId(), callbackQuery);
        masterDataCache.setUsersCurrentBotState(chatId, botState);

        return botStateContext.processInputMessage(botState, callbackQuery.getMessage());
    }

    private void setCurrentDay(String day, ScheduleDto userScheduleData) {
        if (day.equals("MONDAY")) userScheduleData.setMonday(true);
        if (day.equals("TUESDAY")) userScheduleData.setTuesday(true);
        if (day.equals("WEDNESDAY")) userScheduleData.setWednesday(true);
        if (day.equals("THURSDAY")) userScheduleData.setThursday(true);
        if (day.equals("FRIDAY")) userScheduleData.setFriday(true);
    }

    private EditMessageReplyMarkup generateKeyboardChooseButton(long chatId, CallbackQuery callbackQuery) {
        Message message = callbackQuery.getMessage();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        DayOfWeek originalDay = dayOfWeekModeService.getOriginalDay(chatId);
        DayOfWeek targetDay = dayOfWeekModeService.getTargetDay(chatId);


        for (DayOfWeek day : DayOfWeek.values()) {
            buttons.add(List.of(
                    InlineKeyboardButton.builder()
                                        .text(getDayButton(targetDay.getNameDay(), day.getNameDay()))
                                        .callbackData("MASTER_DAY/" + DayOfWeek.valueOf(day.name()))
                                        .build()
            ));
        }

        return EditMessageReplyMarkup.builder()
                                     .chatId(String.valueOf(chatId))
                                     .messageId(message.getMessageId())
                                     .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                                     .build();
    }

    private String getDayButton(String saved, String current) {
        return saved.equals(current) ? current + "✅" : current;
    }
}
