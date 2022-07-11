package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.handler.impl.HandleClientAllRecordsImpl;
import com.bot.eyelashes.repository.MasterRepository;
import com.bot.eyelashes.repository.RecordToMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service("CallbackClientAllRecordsImpl")
public class CallbackClientAllRecordsImpl implements Callback {

    private final MasterRepository masterRepository;
    private final RecordToMasterRepository record;


    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        HandleClientAllRecordsImpl handleClientAllRecords = new HandleClientAllRecordsImpl();
        Long chatId = callbackQuery.getMessage()
                .getChatId();
        List<Long> mastersId = new ArrayList<>();
        for (int i = 0; i < record.findAllByClientId(chatId)
                .size(); i++) {
            mastersId.add(record.findAllByClientId(chatId)
                    .get(i)
                    .getMasterId());
        }
        List<String> allRecordText = handleClientAllRecords.allRecordText(masterRepository.findMasterByTelegramId(), record.findAllByClientId(chatId));
        return SendMessage.builder()
                .replyMarkup(handleClientAllRecords.createInlineKeyboard())
                .text(allRecordText.toString())
                .chatId(chatId.toString())
                .build();
    }
}
