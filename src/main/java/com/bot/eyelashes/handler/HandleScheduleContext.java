package com.bot.eyelashes.handler;

import com.bot.eyelashes.enums.StateSchedule;
import com.bot.eyelashes.handler.schedule.HandleSchedule;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class HandleScheduleContext {
    private final Map<StateSchedule, HandleSchedule> messageHandlers = new HashMap<>();



    public HandleScheduleContext(List<HandleSchedule> messageHandlers) {
        messageHandlers.forEach(handler -> this.messageHandlers.put(handler.getStateSchedule(), handler));
    }

    public SendMessage processInputMessage(StateSchedule currentState, Message message) {
        HandleSchedule currentMessageHandler = findMessageHandler(currentState);
        return currentMessageHandler.getMessage(message);
    }



    private HandleSchedule findMessageHandler(StateSchedule currentState) {
        if (isFillingProfileState(currentState)) {
            return messageHandlers.get(StateSchedule.COUNT_DAY);
        }
        return messageHandlers.get(currentState);
    }

    private boolean isFillingProfileState(StateSchedule currentState) {
        return switch (currentState) {
            case COUNT_DAY, ASK_DATA_TIME, READY, FILLING_SCHEDULE, FILLING_DATA_TIME, FILLED_SCHEDULE->
                    true;
            default -> false;
        };
    }
}
