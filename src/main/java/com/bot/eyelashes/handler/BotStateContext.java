package com.bot.eyelashes.handler;

import com.bot.eyelashes.enums.BotState;
import com.bot.eyelashes.handler.registration.HandleRegistration;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BotStateContext {
    private final Map<BotState, HandleRegistration> messageHandlers = new HashMap<>();

    public BotStateContext(List<HandleRegistration> messageHandlers) {
        messageHandlers.forEach(handler -> this.messageHandlers.put(handler.getHandleName(), handler));
    }

    public SendMessage processInputMessage(BotState currentState, Update update) {
        HandleRegistration currentMessageHandler = findMessageHandler(currentState);
        return currentMessageHandler.getMessage(update.getMessage());
    }

    private HandleRegistration findMessageHandler(BotState currentState) {
        if (isFillingProfileState(currentState)) {
            return messageHandlers.get(BotState.FILLING_PROFILE);
        }
        return messageHandlers.get(currentState);
    }

    private boolean isFillingProfileState(BotState currentState) {
        return switch (currentState) {
            case ASK_READY, ASK_PHONE, ASK_DEFAULT, ASK_ACTIVITY, PROFILE_FIELD, ASK_FULL_NAME, FILLING_PROFILE, REGISTREDET ->
                    true;
            default -> false;
        };
    }
}
