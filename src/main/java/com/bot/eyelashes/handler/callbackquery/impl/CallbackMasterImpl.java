package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.handler.impl.HandleMasterImpl;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;


@RequiredArgsConstructor
public class CallbackMasterImpl implements Callback {
//    private String name;
//    private String surname;
//    private String middlename;
//    private String address;
//    private Long phoneNumber;
//    private Long telegramId;

    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {

        return SendMessage.builder()
                .text("Введите данные")
                .replyMarkup(getHandlerQueryType())
                .chatId(callbackQuery.getMessage()
                        .getChatId()
                        .toString())
                .build();
    }

    private String getMessageByData(CallbackQuery callbackQuery,String data, String text) {
        SendMessage sendMessage = new SendMessage();
        if (callbackQuery.getData().equals(data)) {
            sendMessage.setText(text);
            sendMessage.setChatId(callbackQuery.getMessage().getChatId().toString());
        }
        return callbackQuery.getMessage().getText();
    }


    @Override
    public InlineKeyboardMarkup getHandlerQueryType() {
        HandleMasterImpl handleMaster = new HandleMasterImpl();
        return handleMaster.createInlineKeyboard();
    }
}