package com.bot.eyelashes.handler;

import com.bot.eyelashes.enums.BotState;
import com.bot.eyelashes.handler.callbackquery.CallbackRegistration;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BotStateContext {
    private final Map<BotState, CallbackRegistration> messageHandlers = new HashMap<>();



    public BotStateContext(List<CallbackRegistration> messageHandlers) {
        messageHandlers.forEach(handler -> this.messageHandlers.put(handler.getHandleName(), handler));
    }



    public SendMessage processCallback(BotState currentState, CallbackQuery callbackQuery) {
        CallbackRegistration currentMessageHandler = findMessageHandler(currentState);
        return currentMessageHandler.getCallbackQuery(callbackQuery);
    }





    private CallbackRegistration findMessageHandler(BotState currentState) {
        if (isFillingProfileState(currentState)) {
            return messageHandlers.get(BotState.FILLING_PROFILE);
        }
        return messageHandlers.get(currentState);
    }


    private boolean isFillingProfileState(BotState currentState) {
        return switch (currentState) {
            case ASK_READY, ASK_PHONE, ASK_DEFAULT, ASK_ACTIVITY, PROFILE_FIELD, ASK_FULL_NAME, FILLING_PROFILE, REGISTREDET->
                    true;
            default -> false;
        };
    }
}
