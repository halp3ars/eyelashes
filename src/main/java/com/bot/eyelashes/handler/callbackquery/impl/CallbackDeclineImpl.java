package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.repository.RecordToMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service("CallbackDeclineImpl")
public class CallbackDeclineImpl implements Callback {

    private final RecordToMasterRepository record;

    @Transactional
    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        record.deleteByClientId(callbackQuery.getMessage().getChatId());
        return SendMessage.builder()
                .text("Вы успешно сняты с записи")
                .chatId(callbackQuery.getMessage().getChatId().toString())
                .build();
    }
}
