package com.bot.eyelashes.handler.impl;

import com.bot.eyelashes.handler.Handle;
import com.bot.eyelashes.model.entity.Master;
import com.bot.eyelashes.model.entity.RecordToMaster;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HandleClientAllRecordsImpl implements Handle {

    @Override
    public SendMessage getMessage(Update update) {
        return null;
    }

    @Override
    public InlineKeyboardMarkup createInlineKeyboard() {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(List.of(
                InlineKeyboardButton.builder()
                        .callbackData("DECLINE_FROM_ALL_RECORD")
                        .text("Отменить запись")
                        .build(),
                InlineKeyboardButton.builder()
                        .text("Вернуться в меню")
                        .callbackData("MENU")
                        .build()));
        return InlineKeyboardMarkup.builder()
                .keyboard(buttons)
                .build();
    }

    public List<String> allRecordText(List<Master> master, List<RecordToMaster> records) {
        List<String> message = new ArrayList<>();
        for (int numberOfRecord = 0; numberOfRecord < records.size(); numberOfRecord++) {
            message.add("Вы записаны на - " + records.get(numberOfRecord)
                    .getActivity() +
                    "\nМастер - " + master.get(numberOfRecord)
                    .getName() + " " + master.get(numberOfRecord)
                    .getName() +
                    "\nВремя - " + records.get(numberOfRecord)
                    .getTime() + ":00" +
                    "\nДень недели - " + records.get(numberOfRecord)
                    .getDay() +
                    "\nНомер телефон мастера " + master.get(numberOfRecord)
                    .getPhoneNumber() + "\n"    );
        }
        return message;
    }

}
