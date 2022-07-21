package com.bot.eyelashes.handler.impl;

import com.bot.eyelashes.handler.Handle;
import com.bot.eyelashes.handler.callbackquery.impl.CallbackClientAllRecordsImpl;
import com.bot.eyelashes.model.entity.Master;
import com.bot.eyelashes.model.entity.RecordToMaster;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class HandleClientAllRecordsImpl implements Handle {

    @Override
    public SendMessage getMessage(Update update) {
        return null;
    }

    public InlineKeyboardMarkup getMenuButton() {
        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(
                InlineKeyboardButton.builder()
                        .text("Вернуться в меню")
                        .callbackData("CLIENT")
                        .build());
        return InlineKeyboardMarkup.builder()
                .keyboardRow(firstRow)
                .build();
    }

    @Override
    public InlineKeyboardMarkup createInlineKeyboard() {
        List<InlineKeyboardButton> actionRecord = new ArrayList<>();
        List<InlineKeyboardButton> back = new ArrayList<>();
        back.add(
                InlineKeyboardButton.builder()
                        .text("Вернуться в меню")
                        .callbackData("MENU")
                        .build());
        actionRecord.addAll(List.of(InlineKeyboardButton.builder()
                        .callbackData("DECLINE_ALL_RECORD")
                        .text("Отменить запись")
                        .build(),
                InlineKeyboardButton.builder()
                        .callbackData("CHANGE_RECORD_ALL")
                        .text("Перенести запись")
                        .build()));
        return InlineKeyboardMarkup.builder()
                .keyboardRow(actionRecord)
                .keyboardRow(back)
                .build();
    }

    public List<String> allRecordText(List<Master> master, List<RecordToMaster> records) {
        List<String> message = new ArrayList<>();
        for (int numberOfRecord = 0; numberOfRecord < records.size(); numberOfRecord++) {
            message.add("\n\nВы записаны на - " + records.get(numberOfRecord)
                    .getActivity() +
                    "\nМастер - " + master.get(numberOfRecord)
                    .getName() + " " + master.get(numberOfRecord)
                    .getSurname() +
                    "\nВремя - " + records.get(numberOfRecord)
                    .getTime() + ":00" +
                    "\nАдрес - " + master.get(numberOfRecord)
                    .getAddress() +
                    "\nДень недели - " + records.get(numberOfRecord)
                    .getDay() +
                    "\nНомер телефон мастера " + master.get(numberOfRecord)
                    .getPhoneNumber());
        }
        return message;
    }

}
