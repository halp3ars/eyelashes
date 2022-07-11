package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.cache.MasterDataCache;
import com.bot.eyelashes.enums.BotState;
import com.bot.eyelashes.enums.map.TypeOfActivity;
import com.bot.eyelashes.handler.BotStateContext;
import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.model.dto.MasterDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@RequiredArgsConstructor
@Service("CallbackMasterScheduleImpl")
@Slf4j
public class CallbackMasterScheduleImpl implements Callback {
    private final MasterDataCache masterDataCache;
    private final BotStateContext botStateContext;

    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        MasterDto masterDto = masterDataCache.getUserProfileData(chatId);
        String activity = saveActivityFromCallback(callbackQuery);
        masterDto.setActivity(activity);
        log.info("Master set activity = " + masterDto.getActivity());
        BotState botState = BotState.ASK_ADDRESS;
        masterDataCache.setUsersCurrentBotState(chatId, botState);

        return botStateContext.processInputMessage(botState, callbackQuery.getMessage());
    }

    private String saveActivityFromCallback(CallbackQuery callbackQuery) {
        TypeOfActivity typeOfActivity = new TypeOfActivity();
        if (typeOfActivity.getKey(callbackQuery.getData().split("/")[1])
                .equals(callbackQuery.getData().split("/")[1])) {
            return typeOfActivity.getCommand(callbackQuery.getData().split("/")[1]);
        }

        return "";
    }
}
