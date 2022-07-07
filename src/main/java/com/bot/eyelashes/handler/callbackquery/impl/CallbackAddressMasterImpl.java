package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.cache.MasterDataCache;
import com.bot.eyelashes.enums.BotState;
import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.model.dto.ScheduleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@RequiredArgsConstructor
@Service("CallbackAddressMasterImpl")
public class CallbackAddressMasterImpl implements Callback {

    private final MasterDataCache masterDataCache;

    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage()
                .getChatId();
        ScheduleDto userScheduleData = masterDataCache.getUserScheduleData(chatId);
        int timeTo = Integer.parseInt(callbackQuery.getData().split("/")[1]);
        userScheduleData.setTimeTo(timeTo);
        masterDataCache.saveUserScheduleData(chatId, userScheduleData);
        BotState botState = BotState.REGISTERED;
        masterDataCache.setUsersCurrentBotState(chatId, botState);
        return SendMessage.builder().chatId(chatId.toString()).text("Вы зарегистрированы как мастер")
                .build();
    }
}
