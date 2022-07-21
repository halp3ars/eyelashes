package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.cache.MasterDataCache;
import com.bot.eyelashes.enums.BotState;
import com.bot.eyelashes.handler.BotStateContext;
import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.service.Bot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Service("UpdateProfileMasterCallback")
@RequiredArgsConstructor
public class UpdateProfileMasterCallback implements Callback {
    private final MasterDataCache masterDataCache;
    private final BotStateContext botStateContext;
    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        BotState botState = BotState.ASK_NAME;
        Bot.masterRegistration = true;
        Bot.clientRegistration = false;
        masterDataCache.setUsersCurrentBotState(callbackQuery.getMessage()
                .getChatId(), botState);

        return botStateContext.processInputMessage(botState, callbackQuery.getMessage());
    }
}
