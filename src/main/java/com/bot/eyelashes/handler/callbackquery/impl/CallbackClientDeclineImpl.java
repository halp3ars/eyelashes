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
public class CallbackClientDeclineImpl implements Callback {

    private final RecordToMasterRepository record;

    @Transactional
    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        if (callbackQuery.getData()
                .split("/")[1].equals("ONE_RECORD")) {
            deleteRecord(callbackQuery.getMessage()
                    .getChatId(), CallbackTypeOfActivityImpl.activity.get(callbackQuery.getMessage()
                    .getChatId()));
        } else {
            deleteRecord(callbackQuery.getMessage().getChatId(),callbackQuery.getData().split("/")[1]);
        }
        return SendMessage.builder()
                .text("Вы успешно сняты с записи")
                .chatId(callbackQuery.getMessage()
                        .getChatId()
                        .toString())
                .build();
    }

    public void deleteRecord(Long chatId, String activity) {
        record.deleteByClientIdAndActivity(chatId, activity);
    }
}
