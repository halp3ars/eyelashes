package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.cache.ClientDataCache;
import com.bot.eyelashes.enums.ClientBotState;
import com.bot.eyelashes.handler.ClientBotStateContext;
import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.handler.impl.HandleClientTimeImpl;
import com.bot.eyelashes.repository.RecordToMasterRepository;
import com.bot.eyelashes.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;


@Service("CallbackTimeClientImpl")
@RequiredArgsConstructor
public class CallbackTimeClientImpl implements Callback {

    private final ClientDataCache clientDataCache;
    private final RecordToMasterRepository recordToMasterRepository;
    private final ScheduleRepository scheduleRepository;
    private final ClientBotStateContext clientBotStateContext;
    private SendMessage replyMessage;



    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
      return null;
    }
}
