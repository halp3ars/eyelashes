package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.cache.ClientDataCache;
import com.bot.eyelashes.enums.ClientBotState;
import com.bot.eyelashes.handler.ClientBotStateContext;
import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.repository.RecordToMasterRepository;
import com.bot.eyelashes.service.Bot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import javax.persistence.Entity;
import java.util.List;

@RequiredArgsConstructor
@Service("CallbackChangeDateImpl")
public class CallbackChangeDateImpl implements Callback {

    private final ClientDataCache clientDataCache;
    private final ClientBotStateContext clientBotStateContext;
    private final RecordToMasterRepository recordToMasterRepository ;

    @Override
    @Transactional
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        recordToMasterRepository.deleteByClientIdAndActivity(callbackQuery.getMessage().getChatId(),CallbackTypeOfActivityImpl.activity.get(callbackQuery.getMessage().getChatId()));
        Bot.clientRegistration = true;
        Bot.masterRegistration = false;
        ClientBotState clientBotState = ClientBotState.ASK_CLIENT_DATE;
        clientDataCache.setClientBotState(callbackQuery.getMessage()
                .getChatId(), clientBotState);
        return clientBotStateContext.processInputClientMessage(clientBotState, callbackQuery.getMessage());
    }

}
