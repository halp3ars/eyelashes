package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.handler.impl.HandleCheckRecordImpl;
import com.bot.eyelashes.repository.MasterRepository;
import com.bot.eyelashes.repository.RecordToMasterRepository;
import com.bot.eyelashes.service.Bot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Service("CallbackCheckRecordImpl")
@RequiredArgsConstructor
public class CallbackCheckRecordImpl implements Callback {

    private final MasterRepository masterRepository;

    private final RecordToMasterRepository record;

    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        HandleCheckRecordImpl handleCheckRecord = new HandleCheckRecordImpl(masterRepository,record);
        return handleCheckRecord.getMessageWithCallback(callbackQuery);
    }
}
