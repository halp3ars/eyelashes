package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.cache.MasterDataCache;
import com.bot.eyelashes.enums.BotState;
import com.bot.eyelashes.enums.ClientBotState;
import com.bot.eyelashes.handler.BotStateContext;
import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.handler.impl.HandleMasterTimeFromImpl;
import com.bot.eyelashes.model.dto.ScheduleDto;
import com.bot.eyelashes.service.Bot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Service("CallbackMasterTimeFromImpl")
public class CallbackMasterTimeFromImpl implements Callback {

    private final BotStateContext botStateContext;
    private final MasterDataCache masterDataCache;


    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        BotState botState = BotState.ASK_TIME_FROM;
        masterDataCache.setUsersCurrentBotState(callbackQuery.getMessage().getChatId(), botState);
        return botStateContext.processInputMessage(botState, callbackQuery.getMessage());
    }
}
