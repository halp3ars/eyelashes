package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.enums.map.TypeOfActivity;
import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.handler.impl.HandleCheckRecordImpl;
import com.bot.eyelashes.handler.impl.HandleTypeOfActivityImpl;
import com.bot.eyelashes.model.entity.RecordToMaster;
import com.bot.eyelashes.repository.MasterRepository;
import com.bot.eyelashes.repository.RecordToMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service("CallbackTypeOfActivity")
@RequiredArgsConstructor
public class CallbackTypeOfActivityImpl implements Callback {

    private final MasterRepository masterRepository;
    private final RecordToMasterRepository recordToMasterRepository;
    public static HashMap<Long, String> activity = new HashMap<>();

    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        TypeOfActivity typeOfActivity = new TypeOfActivity();
        HandleTypeOfActivityImpl handleTypeOfActivity = new HandleTypeOfActivityImpl(masterRepository);
        String typeOfActivityCommand = typeOfActivity.getCommand(callbackQuery.getData());
        activity.put(callbackQuery.getMessage()
                .getChatId(), typeOfActivityCommand);
        Optional<RecordToMaster> recordToMaster = recordToMasterRepository.findByClientIdAndActivity(callbackQuery.getMessage()
                .getChatId(), typeOfActivityCommand);
        if (recordToMaster.isPresent()) {
            HandleCheckRecordImpl handleCheckRecord = new HandleCheckRecordImpl(masterRepository, recordToMasterRepository);
            return handleCheckRecord.getMessageWithCallback(callbackQuery);
        } else if (handleTypeOfActivity.createInlineKeyboardWithCallback(callbackQuery)
                .getKeyboard()
                .isEmpty()) {
            List<InlineKeyboardButton> buttons = new ArrayList<>();
            buttons.add(InlineKeyboardButton.builder().text("Назад").callbackData("CLIENT").build());
            return SendMessage.builder()
                    .replyMarkup(InlineKeyboardMarkup.builder().keyboardRow(buttons).build())
                    .chatId(callbackQuery.getMessage()
                            .getChatId()
                            .toString())
                    .text("Мастера по данной категории не представлены")
                    .build();
        } else {
            return SendMessage.builder()
                    .replyMarkup(handleTypeOfActivity.createInlineKeyboardWithCallback(callbackQuery))
                    .chatId(callbackQuery.getMessage()
                            .getChatId()
                            .toString())
                    .text("Выберите мастера к которому хотите записаться")
                    .build();
        }
    }
}

