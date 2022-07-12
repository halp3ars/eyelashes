package com.bot.eyelashes.handler.impl;

import com.bot.eyelashes.handler.Handle;
import com.bot.eyelashes.handler.callbackquery.impl.CallbackTypeOfActivityImpl;
import com.bot.eyelashes.model.entity.Master;
import com.bot.eyelashes.model.entity.RecordToMaster;
import com.bot.eyelashes.repository.MasterRepository;
import com.bot.eyelashes.repository.RecordToMasterRepository;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.*;

@RequiredArgsConstructor
public class HandleCheckRecordImpl implements Handle {


    private final MasterRepository masterRepository;
    private final RecordToMasterRepository record;

    @Override
    public SendMessage getMessage(Update update) {
        return null;
    }

    public SendMessage getMessageWithCallback(CallbackQuery callbackQuery) {
        Long userId = callbackQuery.getMessage()
                .getChatId();
        Optional<RecordToMaster> recordByClientId = record.findByClientIdAndActivity(userId, CallbackTypeOfActivityImpl.activity.get(callbackQuery.getMessage()
                .getChatId()));
        Optional<Master> master = masterRepository.findMasterByTelegramId(recordByClientId.get()
                .getMasterId());
        return SendMessage.builder()
                .chatId(callbackQuery.getMessage()
                        .getChatId()
                        .toString())
                .replyMarkup(createInlineKeyboardWithCallback(callbackQuery))
                .text("Вы записаны на " + master.get()
                        .getActivity()
                        .toLowerCase(Locale.ROOT) + "\nМастер - " + master.get()
                        .getName() + " " + master.get()
                        .getSurname() + "\nВремя - " + recordByClientId.get()
                        .getTime() + ":00" + "\nДень недели - " + recordByClientId.get()
                        .getDay() + "\nАдерс - " + master.get()
                        .getAddress() + "\nНомер телефон мастера  " + master.get()
                        .getPhoneNumber())
                .build();
    }


    @Override
    public InlineKeyboardMarkup createInlineKeyboard() {
        return null;
    }

    public InlineKeyboardMarkup createInlineKeyboardWithCallback(CallbackQuery callbackQuery) {
        Long userId = callbackQuery.getMessage()
                .getChatId();
        Optional<RecordToMaster> recordToMaster = record.findByClientIdAndActivity(userId, CallbackTypeOfActivityImpl.activity.get(callbackQuery.getMessage()
                .getChatId()));
        Long masterId = recordToMaster.get()
                .getMasterId();
        Optional<Master> masterByTelegramId = masterRepository.findMasterByTelegramId(masterId);
        String telegramNick = masterByTelegramId.get()
                .getTelegramNick();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.addAll(List.of(
                InlineKeyboardButton.builder()
                        .text("Отменить запись")
                        .callbackData("DECLINE_RECORD")
                        .build(),
                InlineKeyboardButton.builder()
                        .text("Свзяаться")
                        .url("https://t.me/" + telegramNick)
                        .build(),
                InlineKeyboardButton.builder()
                        .text("Перенести запись")
                        .callbackData("CHANGE_DATE")
                        .build()
        ));
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        row2.add(InlineKeyboardButton.builder()
                .text("Меню")
                .callbackData("MENU")
                .build());
        return InlineKeyboardMarkup.builder()
                .keyboardRow(row1)
                .keyboardRow(row2)
                .build();
    }
}
