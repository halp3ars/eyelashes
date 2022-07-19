package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.handler.impl.HandleClientAllChangeDateImpl;
import com.bot.eyelashes.repository.RecordToMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@RequiredArgsConstructor
@Service("CallbackClientAllChangeDateImpl")
public class CallbackClientAllChangeDateImpl implements Callback {

    private final RecordToMasterRepository record;

    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        HandleClientAllChangeDateImpl handleClientAllChangeDate = new HandleClientAllChangeDateImpl(record);
        return SendMessage.builder()
                .text("Выберите какую запись вы хотите Перенести")
                .chatId(callbackQuery.getMessage()
                        .getChatId()
                        .toString())
                .replyMarkup(handleClientAllChangeDate.createInlineKeyboard(callbackQuery))
                .build();
    }
}
