package com.bot.eyelashes.handler;

import com.bot.eyelashes.enums.BotState;
import com.bot.eyelashes.enums.ClientBotState;
import com.bot.eyelashes.handler.registration.HandleRegistration;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ClientBotStateContext {

    private final Map<ClientBotState, HandleRegistration> messageClientHandlers = new HashMap<>();

    public ClientBotStateContext(List<HandleRegistration> messageClientHandlers){
        messageClientHandlers.forEach(handler -> this.messageClientHandlers.put(handler.getHandleClientName(), handler));
    }

    public SendMessage processInputClientMessage(ClientBotState clientBotState, Message message){
        HandleRegistration handleRegistration = findClientMessageHandler(clientBotState);
        return handleRegistration.getMessage(message);
    }

    private HandleRegistration findClientMessageHandler(ClientBotState clientBotState){
        if(isFillingClientProfile(clientBotState)){
            return messageClientHandlers.get(ClientBotState.FILLING_CLIENT_PROFILE);
        }
        return messageClientHandlers.get(clientBotState);
    }

    private boolean isFillingClientProfile(ClientBotState currentState){
        return switch (currentState) {
            case ASK_CLIENT_FULL_NAME,ASK_CLIENT_PHONE,FILLING_CLIENT_PROFILE,PROFILE_CLIENT_FIELD,CLIENT_REGISTRED->
                    true;
            default -> false;
        };
    }


}
