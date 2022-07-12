package com.bot.eyelashes.handler.callbackquery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service("DayForCheckClientRecordToMaster")
@RequiredArgsConstructor
public class ListClientRecordToMasterCallback implements Callback {


    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {


        return SendMessage.builder()
                          .text("Выберите день")
                          .replyMarkup(keyboardDayMaster())
                          .chatId(callbackQuery.getMessage().getChatId().toString())
                          .build();
    }

    private InlineKeyboardMarkup keyboardDayMaster() {
        List<List<InlineKeyboardButton>> buttonsDayOfWeek = new ArrayList<>();
        buttonsDayOfWeek.add(List.of(
                InlineKeyboardButton.builder().text("Понедельник").callbackData("DAY_CLIENT_RECORD_TO_MASTER/MONDAY").build(),
                InlineKeyboardButton.builder().text("Вторник").callbackData("DAY_CLIENT_RECORD_TO_MASTER/TUESDAY").build(),
                InlineKeyboardButton.builder().text("Среда").callbackData("DAY_CLIENT_RECORD_TO_MASTER/WEDNESDAY").build()
        ));

        buttonsDayOfWeek.add(List.of(
                InlineKeyboardButton.builder().text("Четверг").callbackData("DAY_CLIENT_RECORD_TO_MASTER/THURSDAY").build(),
                InlineKeyboardButton.builder().text("Пятница").callbackData("DAY_CLIENT_RECORD_TO_MASTER/FRIDAY").build(),
                InlineKeyboardButton.builder().text("Суббота").callbackData("DAY_CLIENT_RECORD_TO_MASTER/SATURDAY").build()
        ));

        buttonsDayOfWeek.add(List.of(
                InlineKeyboardButton.builder().text("Воскресенье").callbackData("DAY_CLIENT_RECORD_TO_MASTER/SUNDAY").build()
        ));

        return InlineKeyboardMarkup.builder().keyboard(buttonsDayOfWeek).build();
    }
}
