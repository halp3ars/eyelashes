package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.handler.impl.HandleRecordToMasterImpl;
import com.bot.eyelashes.repository.MasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Service("CallbackRecordToMasterImpl")
@RequiredArgsConstructor
public class CallbackRecordToMasterImpl implements Callback {


    private final MasterRepository masterRepository;

    @Override
    public SendMessage getMessageByCallback(CallbackQuery callbackQuery) {
        HandleRecordToMasterImpl handleRecordToMaster = new HandleRecordToMasterImpl(masterRepository);
        return SendMessage.builder().replyMarkup(handleRecordToMaster.createInlineKeyboard()).build();
    }
}