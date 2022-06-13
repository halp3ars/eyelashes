package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.handler.impl.HandleCheckRecordImpl;
import com.bot.eyelashes.model.entity.Master;
import com.bot.eyelashes.model.entity.RecordToMaster;
import com.bot.eyelashes.repository.MasterRepository;
import com.bot.eyelashes.repository.RecordToMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service("CallbackRecordImpl")
public class CallbackRecordImpl implements Callback {


    private final RecordToMasterRepository record;

    private final MasterRepository masterRepository;

    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        Long userId = callbackQuery.getMessage().getChatId();
        Optional<RecordToMaster> byClientId = record.findByClientId(userId);
        if (byClientId.isEmpty()) {
            return SendMessage.builder()
                    .chatId(callbackQuery.getMessage()
                            .getChatId()
                            .toString())
                    .text("Напишите пожалуйста \uD83D\uDE4F 'clientRegistration' ")
                    .build();
        } else {
            HandleCheckRecordImpl handleCheckRecord = new HandleCheckRecordImpl(masterRepository, record);
            Optional<Master> master = masterRepository.findByTelegramId(byClientId.get().getMasterId());

            return SendMessage.builder()
                    .chatId(callbackQuery.getMessage()
                            .getChatId()
                            .toString())
                    .replyMarkup(handleCheckRecord.createInlineKeyboardWithCallback(callbackQuery))
                    .text("Вы записаны к " + master.get().getName() + " " + master.get().getSurname() +
                     " На " + byClientId.get().getTime()  + " "  + byClientId.get().getDate())
                    .build();
        }

    }
}
