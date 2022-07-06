package com.bot.eyelashes.handler.callbackquery.impl;


import com.bot.eyelashes.cache.MasterDataCache;
import com.bot.eyelashes.enums.BotState;
import com.bot.eyelashes.enums.ClientBotState;
import com.bot.eyelashes.handler.BotStateContext;
import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.model.dto.RecordToMasterDto;
import com.bot.eyelashes.model.dto.ScheduleDto;
import com.bot.eyelashes.service.Bot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;


@RequiredArgsConstructor
@Service("CallbackMasterRegisteredImpl")
public class CallbackMasterRegisteredImpl implements Callback {

    private final MasterDataCache masterDataCache;
    private final BotStateContext botStateContext;

    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage()
                .getChatId();
        ScheduleDto userScheduleData = masterDataCache.getUserScheduleData(chatId);
        userScheduleData.setTimeTo(Integer.parseInt(callbackQuery.getData()
                .split("/")[1]));
        masterDataCache.saveUserScheduleData(chatId,userScheduleData);
        BotState botState = BotState.REGISTERED;
        masterDataCache.setUsersCurrentBotState(chatId,botState);
        return botStateContext.processInputMessage(botState, callbackQuery.getMessage());
    }
}
