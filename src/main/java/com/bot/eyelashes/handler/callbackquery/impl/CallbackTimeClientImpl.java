package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.cache.ClientDataCache;
import com.bot.eyelashes.enums.ClientBotState;
import com.bot.eyelashes.handler.ClientBotStateContext;
import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.model.dto.RecordToMasterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;


@Service("CallbackTimeClientImpl")
@RequiredArgsConstructor
public class CallbackTimeClientImpl implements Callback {

    private final ClientDataCache clientDataCache;
    private final ClientBotStateContext clientBotStateContext;

    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage()
                .getChatId();
        RecordToMasterDto recordToMasterDto = clientDataCache.getRecordData(chatId);
        recordToMasterDto.setTime(callbackQuery.getData()
                   .split("/")[1]);
        ClientBotState clientBotState = ClientBotState.PROFILE_CLIENT_FIELD;
        clientDataCache.setClientBotState(callbackQuery.getMessage()
                .getChatId(), clientBotState);
        clientDataCache.saveRecordData(chatId, recordToMasterDto);
        return clientBotStateContext.processInputClientMessage(clientBotState, callbackQuery.getMessage());
    }
}
