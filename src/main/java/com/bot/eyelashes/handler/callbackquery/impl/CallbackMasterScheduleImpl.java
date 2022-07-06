package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.cache.MasterDataCache;
import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.handler.impl.HandleScheduleMasterImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@RequiredArgsConstructor
@Service("CallbackMasterScheduleImpl")
public class CallbackMasterScheduleImpl implements Callback {

    private final MasterDataCache masterDataCache;

    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        HandleScheduleMasterImpl handleScheduleMaster = new HandleScheduleMasterImpl(masterDataCache);
        return SendMessage.builder().text("Выберите дни в которые вы хотиете работать").chatId(callbackQuery.getMessage().getChatId().toString()).replyMarkup(handleScheduleMaster.createInlineKeyboardWithCallback(callbackQuery)).build();
    }
}
