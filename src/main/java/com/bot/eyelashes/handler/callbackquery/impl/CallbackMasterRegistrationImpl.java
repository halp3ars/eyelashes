package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.cache.MasterDataCache;
import com.bot.eyelashes.enums.BotState;
import com.bot.eyelashes.handler.BotStateContext;
import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.repository.MasterRepository;
import com.bot.eyelashes.service.Bot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;


@RequiredArgsConstructor
@Service("CallbackMasterRegistrationImpl")
public class CallbackMasterRegistrationImpl implements Callback {

    private final BotStateContext botStateContext;

    private final MasterDataCache masterDataCache;
    private final MasterRepository masterRepository;


    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        if (masterRepository.existsByTelegramId(callbackQuery.getMessage()
                .getChatId())) {
            return SendMessage.builder()
                    .chatId(callbackQuery.getMessage()
                            .getChatId()
                            .toString())
                    .text("Вы авторизированы")
                    .build();
        }
        BotState botState = BotState.ASK_NAME;
        Bot.masterRegistration = true;
        Bot.clientRegistration = false;
        masterDataCache.setUsersCurrentBotState(callbackQuery.getMessage()
                .getChatId(), botState);
        return botStateContext.processInputMessage(botState, callbackQuery.getMessage());
    }
}
