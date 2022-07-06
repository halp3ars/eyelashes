package com.bot.eyelashes.handler;

import com.bot.eyelashes.enums.BotState;
import com.bot.eyelashes.handler.registration.HandleRegistration;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BotStateContext {

    private final Map<BotState, HandleRegistration> messageHandlers = new HashMap<>();

    public BotStateContext(List<HandleRegistration> messageHandlers) {
        messageHandlers.forEach(handler -> this.messageHandlers.put(handler.getHandleName(), handler));
    }


    public SendMessage processInputMessage(BotState botState, Message message) {
        HandleRegistration handleRegistration = findMessageHandler(botState);
        return handleRegistration.getMessage(message);
    }

    private HandleRegistration findMessageHandler(BotState botState) {
        if (isFillingProfileState(botState)) {
            return messageHandlers.get(BotState.FILLING_PROFILE);
        }
        return messageHandlers.get(botState);
    }

    private boolean isFillingProfileState(BotState botState) {
        return switch (botState) {
            case ASK_READY, ASK_SURNAME, ASK_PHONE, ASK_DEFAULT, ASK_ACTIVITY, PROFILE_FIELD, ASK_NAME, FILLING_PROFILE, REGISTREDET->
                    true;
            default -> false;
        };
    }




}
