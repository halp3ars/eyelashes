package com.bot.eyelashes.handler.schedule;

import com.bot.eyelashes.enums.StateSchedule;
import com.bot.eyelashes.handler.callbackquery.CallbackSchedule;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ScheduleStateContext {
    private final Map<StateSchedule, CallbackSchedule> scheduleHandler = new HashMap<>();

    public ScheduleStateContext(List<CallbackSchedule> scheduleHandler) {
        scheduleHandler.forEach(handler -> this.scheduleHandler.put(handler.getHandleName(), handler));
    }

    public SendMessage processCallback(StateSchedule currentState, CallbackQuery callbackQuery) {
        CallbackSchedule currentScheduleHandler = findMessageHandler(currentState);
        return currentScheduleHandler.getCallbackQuery(callbackQuery);
    }

    private CallbackSchedule findMessageHandler(StateSchedule currentState) {
        if (isFillingSchedule(currentState)) {
            return scheduleHandler.get(StateSchedule.FILLING_SCHEDULE);
        }
        return scheduleHandler.get(currentState);
    }

    private boolean isFillingSchedule(StateSchedule currentState) {
        return switch (currentState) {
            case COUNT_DAY, ASK_DATA_TIME, READY, FILLING_SCHEDULE, FILLING_DATA_TIME, FILLED_SCHEDULE -> true;
            default -> false;
        };
    }
}
