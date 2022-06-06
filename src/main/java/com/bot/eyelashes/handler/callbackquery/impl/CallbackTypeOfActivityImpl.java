package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.handler.impl.HandleTypeOfActivityImpl;
import com.bot.eyelashes.repository.MasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Service("CallbackTypeOfActivity")
@RequiredArgsConstructor
public class CallbackTypeOfActivityImpl implements Callback {

    private final MasterRepository masterRepository;

    @Override
    public SendMessage getMessageByCallback(CallbackQuery callbackQuery) {
        return SendMessage.builder()
                .replyMarkup(getMarkup())
                .chatId(callbackQuery.getMessage()
                        .getChatId()
                        .toString())
                .text("Выберите мастера к которому хотите записаться")
                .build();
    }

    @Override
    public InlineKeyboardMarkup getMarkup() {
        HandleTypeOfActivityImpl handleTypeOfActivity = new HandleTypeOfActivityImpl(masterRepository);
        return handleTypeOfActivity.createInlineKeyboard();
    }
}
