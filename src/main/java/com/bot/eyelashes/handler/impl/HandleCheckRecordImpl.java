package com.bot.eyelashes.handler.impl;

import com.bot.eyelashes.handler.Handle;
import com.bot.eyelashes.handler.callbackquery.impl.CallbackCheckRecordImpl;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        Optional<RecordToMaster> recordByClientId = record.findByClientIdAndActivity(userId, CallbackTypeOfActivityImpl.activity);
        Optional<Master> master = masterRepository.findMasterByTelegramId(recordByClientId.get()
                .getMasterId());
        return SendMessage.builder()
                .chatId(callbackQuery.getMessage()
                        .getChatId()
                        .toString())
                .replyMarkup(createInlineKeyboardWithCallback(callbackQuery))
                .text("Вы записаны к " + master.get()
                        .getName() + " " + master.get()
                        .getSurname() +
                        "\nНа " + master.get()
                        .getActivity() + " в " + recordByClientId.get()
                        .getTime() + " " + recordByClientId.get()
                        .getDay() + "\nПо адресу " + master.get()
                        .getAddress() + "\nНомер телефона мастера " + master.get()
                        .getPhoneNumber())
                .build();
    }

    @Override
    public InlineKeyboardMarkup createInlineKeyboard() {
        return null;
    }

    public InlineKeyboardMarkup createInlineKeyboardWithCallback(CallbackQuery callbackQuery) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        Long userId = callbackQuery.getMessage()
                .getChatId();
        Optional<RecordToMaster> recordToMaster = record.findByClientIdAndActivity(userId, CallbackTypeOfActivityImpl.activity );
        Long masterId = recordToMaster.get()
                .getMasterId();
        Optional<Master> masterByTelegramId = masterRepository.findMasterByTelegramId(masterId);
        String telegramNick = masterByTelegramId.get()
                .getTelegramNick();
        buttons.add(Arrays.asList(
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
        inlineKeyboardMarkup.setKeyboard(buttons);
        return inlineKeyboardMarkup;
    }
}
