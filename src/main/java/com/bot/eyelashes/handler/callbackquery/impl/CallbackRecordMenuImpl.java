package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.enums.map.TypeOfActivity;
import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.handler.impl.HandleCheckRecordImpl;
import com.bot.eyelashes.handler.impl.HandleRecordMenuImpl;
import com.bot.eyelashes.repository.MasterRepository;
import com.bot.eyelashes.repository.RecordToMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.HashMap;

@Service("CallbackRecordMenuImpl")
@RequiredArgsConstructor
public class CallbackRecordMenuImpl implements Callback {

    private final MasterRepository masterRepository;


    public static HashMap<Long,String> masterActivityForRecord = new HashMap<>();


    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        HandleRecordMenuImpl handleRecordToMaster = new HandleRecordMenuImpl(masterRepository);
        TypeOfActivity typeOfActivity = new TypeOfActivity();
        String callbackData = callbackQuery.getData();
        masterActivityForRecord.put(callbackQuery.getMessage().getChatId(),typeOfActivity.getCommand(callbackData.substring(callbackData.indexOf("/"),callbackData.lastIndexOf("/"))));
        return SendMessage.builder()
                .replyMarkup(handleRecordToMaster.createInlineKeyboardWithCallback(callbackQuery))
                .chatId(callbackQuery.getMessage()
                        .getChatId()
                        .toString())
                .text("Вы хотитите записаться \uD83D\uDCC5 или свзяаться \uD83D\uDCDE?")
                .build();

    }
}
