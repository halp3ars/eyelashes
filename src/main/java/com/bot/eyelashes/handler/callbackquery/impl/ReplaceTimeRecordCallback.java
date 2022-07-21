package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.cache.ReplaceRecordMasterDataCache;
import com.bot.eyelashes.enums.map.ScheduleMasterMap;
import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.model.dto.RecordToMasterDto;
import com.bot.eyelashes.model.entity.RecordToMaster;
import com.bot.eyelashes.repository.RecordToMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("ReplaceTimeRecordCallback")
@RequiredArgsConstructor
public class ReplaceTimeRecordCallback implements Callback {
    private final ReplaceRecordMasterDataCache replaceRecordMasterDataCache;
    private final RecordToMasterRepository recordToMasterRepository;

    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        RecordToMasterDto recordToMasterDto = new RecordToMasterDto();
        Long chatId = callbackQuery.getMessage().getChatId();
        ScheduleMasterMap scheduleMasterMap = new ScheduleMasterMap();
        String day = scheduleMasterMap.getDay(callbackQuery.getData().split("/")[1]);
        Long clientId = Long.parseLong(callbackQuery.getData().split("/")[2]);
        List<RecordToMaster> records = recordToMasterRepository.findByMasterId(chatId);
        for (RecordToMaster record : records) {
            if (record.getClientId() == clientId) {
                recordToMasterDto = replaceRecordMasterDataCache.setToDto(record);

            }
        }
        recordToMasterDto.setDay(day);

        replaceRecordMasterDataCache.setRecordToMasterData(chatId, recordToMasterDto);

        return SendMessage.builder()
                .chatId(callbackQuery.getMessage().getChatId().toString())
                .text("Выберите время")
                .replyMarkup(createInlineKeyboard(clientId))
                .build();
    }

    public InlineKeyboardMarkup createInlineKeyboard(Long clientId) {
        List<InlineKeyboardButton> rowMain = new ArrayList<>();
        List<InlineKeyboardButton> rowSecond = new ArrayList<>();
        List<InlineKeyboardButton> rowThird = new ArrayList<>();

        final int MAX_WORK_TIME = 20;
        for (int hours = 8; hours < MAX_WORK_TIME; hours++) {
            if (hours <= 11) {
                rowMain.add(InlineKeyboardButton.builder()
                        .text(hours + " : 00")
                        .callbackData("REPLACE_TIME_RECORD_TO_MASTER/" + hours + "/" + clientId)
                        .build());
            } else if (hours <= 15) {
                rowSecond.add(InlineKeyboardButton.builder()
                        .text(hours + " : 00")
                        .callbackData("REPLACE_TIME_RECORD_TO_MASTER/" + hours + "/" + clientId)
                        .build());
            } else {
                rowThird.add(InlineKeyboardButton.builder()
                        .text(hours + " : 00")
                        .callbackData("REPLACE_TIME_RECORD_TO_MASTER/" + hours + "/" + clientId)
                        .build());
            }
        }

        return InlineKeyboardMarkup.builder()
                .keyboardRow(rowMain)
                .keyboardRow(rowSecond)
                .keyboardRow(rowThird)
                .build();
    }
}
