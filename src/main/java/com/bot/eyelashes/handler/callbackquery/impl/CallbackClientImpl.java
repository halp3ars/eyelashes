package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.handler.impl.HandleCheckRecordImpl;
import com.bot.eyelashes.handler.impl.HandleClientImpl;
import com.bot.eyelashes.model.entity.RecordToMaster;
import com.bot.eyelashes.repository.MasterRepository;
import com.bot.eyelashes.repository.RecordToMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Optional;

@RequiredArgsConstructor
@Service("CallbackClientImpl")
public class CallbackClientImpl implements Callback {

    private final MasterRepository masterRepository;

    private final RecordToMasterRepository record;

    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
//        Long userId = callbackQuery.getMessage()
//                .getChatId();
//        Optional<RecordToMaster> byClientId = record.findByClientId(userId);
//        if (byClientId.isPresent()) {
//            HandleCheckRecordImpl handleCheckRecord = new HandleCheckRecordImpl(masterRepository, record);
//            return handleCheckRecord.getMessageWithCallback(callbackQuery);
//        } else {
            HandleClientImpl handleClient = new HandleClientImpl();
            return SendMessage.builder()
                    .chatId(callbackQuery.getMessage()
                            .getChatId()
                            .toString())
                    .replyMarkup(handleClient.createInlineKeyboard())
                    .text("Виды услуг")
                    .build();
//        }
    }
}
