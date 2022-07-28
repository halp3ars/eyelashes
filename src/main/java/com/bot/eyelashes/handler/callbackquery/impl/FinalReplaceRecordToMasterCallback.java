package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.cache.ReplaceRecordMasterDataCache;
import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.model.dto.RecordToMasterDto;
import com.bot.eyelashes.model.entity.RecordToMaster;
import com.bot.eyelashes.repository.RecordToMasterRepository;
import com.bot.eyelashes.service.Bot;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service("FinalReplaceRecordToMasterCallback")
@RequiredArgsConstructor
public class FinalReplaceRecordToMasterCallback implements Callback {

    private final ReplaceRecordMasterDataCache replaceRecordMasterDataCache;
    private final RecordToMasterRepository recordToMasterRepository;
    private long clientId;
    private String activity;

    @Transactional
    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) throws TelegramApiException {
        Long chatId = callbackQuery.getMessage()
                .getChatId();
        String time = callbackQuery.getData()
                .split("/")[1];
        long clientIdFromCallback = Long.parseLong(callbackQuery.getData()
                .split("/")[2]);

        List<RecordToMaster> recordMaster = recordToMasterRepository.findByMasterId(chatId);
        for (RecordToMaster record : recordMaster) {
            if (clientIdFromCallback == record.getClientId()) {
                clientId = record.getClientId();
                activity = record.getActivity();
            }
        }
        RecordToMasterDto recordToMasterData = replaceRecordMasterDataCache.getRecordToMasterData(chatId);
        recordToMasterData.setTime(time);
        recordToMasterData.setMasterId(callbackQuery.getMessage()
                .getChatId());
        recordToMasterData.setClientId(clientId);
        recordToMasterData.setActivity(activity);
        replaceRecordMasterDataCache.deleteRecordByMasterId(chatId, clientId, activity);
        replaceRecordMasterDataCache.saveRecord(recordToMasterData);
        sendAlert();
        return SendMessage.builder()
                .text("Запись перенесена. Вернитесь в главное меню")
                .replyMarkup(keyboardMenu())
                .chatId(String.valueOf(chatId))
                .build();
    }

    private SendMessage sendAlert() {
        return SendMessage.builder()
                .text("Ваша запись перенесена")
                .chatId(clientId)
                .build();
    }

    private InlineKeyboardMarkup keyboardMenu() {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(Collections.singletonList(
                InlineKeyboardButton.builder()
                        .text("Меню")
                        .callbackData("MENU")
                        .build()
        ));
        return InlineKeyboardMarkup.builder()
                .keyboard(buttons)
                .build();
    }
}
