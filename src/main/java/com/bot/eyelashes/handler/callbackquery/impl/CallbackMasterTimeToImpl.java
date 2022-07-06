package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.cache.MasterDataCache;
import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.handler.impl.HandleMasterTimeToImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;


@RequiredArgsConstructor
@Service("CallbackMasterTimeToImpl")
public class CallbackMasterTimeToImpl implements Callback {

    private final MasterDataCache masterDataCache;

    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        HandleMasterTimeToImpl handleMasterTimeTo = new HandleMasterTimeToImpl(masterDataCache);
        return SendMessage.builder().chatId(callbackQuery.getMessage().getChatId().toString()).replyMarkup(handleMasterTimeTo.createInlineKeyboard(callbackQuery)).text("Выберите время По которое вы хотите работать")
                .build();
    }
}
