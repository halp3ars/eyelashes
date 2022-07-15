package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.handler.callbackquery.Callback;
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
@Service("CallbackDeclineImpl")
public class CallbackClientDeclineImpl implements Callback {

    private final RecordToMasterRepository record;

    @Transactional
    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        if (callbackQuery.getData()
                .split("/")[1].equals("ONE_RECORD")) {
            deleteRecord(callbackQuery.getMessage()
                    .getChatId(), CallbackTypeOfActivityImpl.activity.get(callbackQuery.getMessage()
                    .getChatId()));
        } else {
            deleteRecord(callbackQuery.getMessage().getChatId(),callbackQuery.getData().split("/")[1]);
        }
        return SendMessage.builder()
                .text("Вы успешно сняты с записи")
                .chatId(callbackQuery.getMessage()
                        .getChatId()
                        .toString())
                .replyMarkup(getInlineKeyboard())
                .build();
    }

    public void deleteRecord(Long chatId, String activity) {
        record.deleteByClientIdAndActivity(chatId, activity);
    }

    private InlineKeyboardMarkup getInlineKeyboard(){
        List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList<>();
        inlineKeyboardButtonList.add(InlineKeyboardButton.builder().callbackData("MENU").text("Меню").build());
        return InlineKeyboardMarkup.builder().keyboardRow(inlineKeyboardButtonList).build();
    }

}
