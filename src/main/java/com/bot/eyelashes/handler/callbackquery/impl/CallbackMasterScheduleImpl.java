package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.cache.MasterDataCache;
import com.bot.eyelashes.enums.BotState;
import com.bot.eyelashes.enums.map.TypeOfActivity;
import com.bot.eyelashes.handler.BotStateContext;
import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.model.dto.MasterDto;
import com.bot.eyelashes.model.dto.ScheduleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@RequiredArgsConstructor
@Service("CallbackMasterScheduleImpl")
public class CallbackMasterScheduleImpl implements Callback {

    private final MasterDataCache masterDataCache;

    private final BotStateContext botStateContext;

    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage()
                .getChatId();
        ScheduleDto userScheduleData = masterDataCache.getUserScheduleData(chatId);
        String day = callbackQuery.getData()
                .split("/" )[1];
        if(day.equals("MONDAY")) userScheduleData.setMonday(true);
        if(day.equals("TUESDAY")) userScheduleData.setTuesday(true);
        if(day.equals("WEDNESDAY")) userScheduleData.setWednesday(true);
        if(day.equals("THURSDAY")) userScheduleData.setThursday(true);
        if(day.equals("FRIDAY")) userScheduleData.setFriday(true);
        userScheduleData.setTelegramId(chatId);
        MasterDto masterDto = masterDataCache.getUserProfileData(chatId);
        TypeOfActivity typeOfActivity = new TypeOfActivity();
        masterDto.setActivity(typeOfActivity.getCommand(callbackQuery.getData().split("/")[1]));
        masterDataCache.saveUserProfileData(chatId,masterDto);
        masterDataCache.saveUserScheduleData(chatId, userScheduleData);
        BotState botState = BotState.ASK_DATE;
        masterDataCache.setUsersCurrentBotState(chatId,botState);
        return botStateContext.processInputMessage(botState, callbackQuery.getMessage());
    }
}
