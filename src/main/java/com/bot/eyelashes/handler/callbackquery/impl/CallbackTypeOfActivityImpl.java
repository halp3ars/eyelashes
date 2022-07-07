package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.enums.map.TypeOfActivity;
import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.handler.impl.HandleCheckRecordImpl;
import com.bot.eyelashes.handler.impl.HandleTypeOfActivityImpl;
import com.bot.eyelashes.model.entity.RecordToMaster;
import com.bot.eyelashes.repository.MasterRepository;
import com.bot.eyelashes.repository.RecordToMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Optional;

@Service("CallbackTypeOfActivity")
@RequiredArgsConstructor
public class CallbackTypeOfActivityImpl implements Callback {

    private final MasterRepository masterRepository;
    private final RecordToMasterRepository recordToMasterRepository;
    public static String activity;

    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        TypeOfActivity typeOfActivity = new TypeOfActivity();
        String typeOfActivityCommand = typeOfActivity.getCommand(callbackQuery.getData());
        activity = typeOfActivityCommand;
        Optional<RecordToMaster> recordToMaster = recordToMasterRepository.findByClientIdAndActivity(callbackQuery.getMessage()
                .getChatId(), typeOfActivityCommand);
        if (recordToMaster.isPresent()) {
            HandleCheckRecordImpl handleCheckRecord = new HandleCheckRecordImpl(masterRepository, recordToMasterRepository);
            return handleCheckRecord.getMessageWithCallback(callbackQuery);
        } else {
            HandleTypeOfActivityImpl handleTypeOfActivity = new HandleTypeOfActivityImpl(masterRepository);
            return SendMessage.builder()
                    .replyMarkup(handleTypeOfActivity.createInlineKeyboardWithCallback(callbackQuery))
                    .chatId(callbackQuery.getMessage()
                            .getChatId()
                            .toString())
                    .text("Выберите мастера к которому хотите записаться")
                    .build();
        }
    }
}

