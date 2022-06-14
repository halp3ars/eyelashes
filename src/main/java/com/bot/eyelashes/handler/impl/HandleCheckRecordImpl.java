package com.bot.eyelashes.handler.impl;

import com.bot.eyelashes.handler.Handle;
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
    public SendMessage getMessage(Update update){
        return null;
    }

    public SendMessage getMessageWithCallback(CallbackQuery callbackQuery){
        Long userId = callbackQuery.getMessage()
                .getChatId();
        Optional<RecordToMaster> byClientId = record.findByClientId(userId);
        Optional<Master> master = masterRepository.findByTelegramId(byClientId.get()
                .getMasterId());
        return SendMessage.builder()
                .chatId(callbackQuery.getMessage()
                        .getChatId()
                        .toString())
                .replyMarkup(createInlineKeyboardWithCallback(callbackQuery))
                .text("Вы записаны к " + master.get()
                        .getName() + " " + master.get()
                        .getSurname() +
                        " На " + byClientId.get()
                        .getTime() + " " + byClientId.get()
                        .getDate())
                .build();
    }

    @Override
    public InlineKeyboardMarkup createInlineKeyboard() {
        return null;
    }

    public InlineKeyboardMarkup createInlineKeyboardWithCallback(CallbackQuery callbackQuery) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        Long userId = callbackQuery.getMessage().getChatId();
        Optional<RecordToMaster> recordToMaster = record.findByClientId(userId);
        Long masterId = recordToMaster.get().getMasterId();
        Optional<Master> masterByTelegramId = masterRepository.findByTelegramId(masterId);
        String phoneNumber = masterByTelegramId.get().getPhoneNumber();
        buttons.add(Arrays.asList(
                InlineKeyboardButton.builder().text("Отменить запись").callbackData("DECLINE_RECORD").build(),
                InlineKeyboardButton.builder().text("Свзяаться").url("https://t.me/" + phoneNumber ).build()
//                InlineKeyboardButton.builder().text("Перенести запись").callbackData("CHANGE_DATE").build()
        ));
        inlineKeyboardMarkup.setKeyboard(buttons);
        return inlineKeyboardMarkup;
    }
}
