package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.cache.MasterDataCache;
import com.bot.eyelashes.enums.BotState;
import com.bot.eyelashes.handler.BotStateContext;
import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.handler.impl.HandleMasterTimeToImpl;
import com.bot.eyelashes.model.dto.ScheduleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;


@RequiredArgsConstructor
@Service("CallbackMasterTimeToImpl")
public class CallbackMasterTimeToImpl implements Callback {

    private final MasterDataCache masterDataCache;
    private final BotStateContext botStateContext;
    public static int time;

    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        long chatId = callbackQuery.getMessage().getChatId();
        int timeFrom = Integer.parseInt(callbackQuery.getData().split("/")[1]);
        time = timeFrom;
        ScheduleDto userScheduleData = masterDataCache.getUserScheduleData(chatId);

        userScheduleData.setTelegramId(chatId);
        userScheduleData.setTimeFrom(timeFrom);

        BotState botState = BotState.ASK_TIME_TO;
        masterDataCache.saveUserScheduleData(chatId,userScheduleData);
        masterDataCache.setUsersCurrentBotState(chatId, botState);
        return botStateContext.processInputMessage(botState, callbackQuery.getMessage());
    }
}
