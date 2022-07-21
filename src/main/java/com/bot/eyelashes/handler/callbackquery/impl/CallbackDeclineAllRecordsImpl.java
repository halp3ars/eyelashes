package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.repository.ClientRepository;
import com.bot.eyelashes.repository.RecordToMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service("CallbackDeclineAllRecordsImpl")
public class CallbackDeclineAllRecordsImpl implements Callback {

    private final RecordToMasterRepository record;

    @Override
    @Transactional
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        record.deleteAllByClientId(callbackQuery.getMessage().getChatId());
        return SendMessage.builder().chatId(callbackQuery.getMessage().getChatId()).text("Вы успешно сняты со всех записей").replyMarkup(createMenuButton()).build();
    }

    private InlineKeyboardMarkup createMenuButton(){
        List<InlineKeyboardButton> menuRow = new ArrayList<>();
        menuRow.add(InlineKeyboardButton.builder().callbackData("MENU").text("Меню").build());
        return InlineKeyboardMarkup.builder().keyboardRow(menuRow).build();
    }



}
