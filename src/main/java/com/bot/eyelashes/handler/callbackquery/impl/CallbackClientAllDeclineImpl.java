package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.handler.impl.HandleClientAllDeclineImpl;
import com.bot.eyelashes.repository.RecordToMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@RequiredArgsConstructor
@Service("CallbackClientAllDeclineImpl")
public class CallbackClientAllDeclineImpl implements Callback {

    private final RecordToMasterRepository recordToMasterRepository;

    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        HandleClientAllDeclineImpl handleClientAllDecline = new HandleClientAllDeclineImpl(recordToMasterRepository);
        return SendMessage.builder()
                .text("Выберите какую запись вы хотите отменить")
                .chatId(callbackQuery.getMessage()
                        .getChatId()
                        .toString())
                .replyMarkup(handleClientAllDecline.createInlineKeyboard(callbackQuery))
                .build();
    }
}
