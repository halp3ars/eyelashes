package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.handler.impl.HandleRecordMenuImpl;
import com.bot.eyelashes.repository.MasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;


@Service("CallbackRecordMenuImpl")
@RequiredArgsConstructor
public class CallbackRecordMenuImpl implements Callback {

    private final MasterRepository masterRepository;

    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        HandleRecordMenuImpl handleRecordToMaster = new HandleRecordMenuImpl(masterRepository);
        return SendMessage.builder()
                .replyMarkup(handleRecordToMaster.createInlineKeyboardWithCallback(callbackQuery))
                .chatId(callbackQuery.getMessage()
                        .getChatId()
                        .toString())
                .text("Вы хотитите записаться \uD83D\uDCC5 или свзяаться \uD83D\uDCDE?")
                .build();

    }
}
