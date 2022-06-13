package com.bot.eyelashes.handler.schedule;

import com.bot.eyelashes.enums.StateSchedule;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ScheduleStateContext {
    private final Map<StateSchedule, HandleSchedule> scheduleHandler = new HashMap<>();

    public ScheduleStateContext(List<HandleSchedule> scheduleHandler) {
        scheduleHandler.forEach(handler -> this.scheduleHandler.put(handler.getStateSchedule(), handler));
    }

    public SendMessage processInputMessage(StateSchedule currentState, Message message) {
        HandleSchedule currentScheduleHandler = findMessageHandler(currentState);
        return currentScheduleHandler.getMessage(message);
    }

    private HandleSchedule findMessageHandler(StateSchedule currentState) {
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
