package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.handler.impl.HandleCheckRecordImpl;
import com.bot.eyelashes.model.dto.RecordToMasterDto;
import com.bot.eyelashes.model.entity.Master;
import com.bot.eyelashes.model.entity.RecordToMaster;
import com.bot.eyelashes.repository.MasterRepository;
import com.bot.eyelashes.repository.RecordToMasterRepository;
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
        Long userId = callbackQuery.getMessage().getFrom().getId();
        return SendMessage.builder()
                .chatId(callbackQuery.getMessage()
                        .getChatId()
                        .toString())
                .replyMarkup(handleCheckRecord.createInlineKeyboardWithCallback(callbackQuery))
                .text("asd")
                .build();
    }
}
