package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.cache.MasterDataCache;
import com.bot.eyelashes.enums.BotState;
import com.bot.eyelashes.handler.BotStateContext;
import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.repository.MasterRepository;
import com.bot.eyelashes.service.Bot;
import com.bot.eyelashes.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service("CallbackMasterRegistrationImpl")
public class CallbackMasterRegistrationImpl implements Callback {
    private final BotStateContext botStateContext;
    private final MasterDataCache masterDataCache;
    private final MasterRepository masterRepository;
    private final MessageService messageService;


    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        if (masterRepository.existsByTelegramId(callbackQuery.getMessage().getChatId())) {

            return messageService.getReplyMessageWithKeyboard(callbackQuery.getMessage().getChatId(),
                    "Выберите действие",
                    keyboardForAuthMaster());
        }

        BotState botState = BotState.ASK_NAME;
        Bot.masterRegistration = true;
        Bot.clientRegistration = false;
        masterDataCache.setUsersCurrentBotState(callbackQuery.getMessage()
                .getChatId(), botState);

        return botStateContext.processInputMessage(botState, callbackQuery.getMessage());
    }

    private InlineKeyboardMarkup keyboardForAuthMaster() {
        List<InlineKeyboardButton> rowMain = new ArrayList<>();
        List<InlineKeyboardButton> rowSecond = new ArrayList<>();
        rowMain.add(
                InlineKeyboardButton.builder()
                        .text("Профиль")
                        .callbackData("MASTER_PROFILE")
                        .build()
        );
        rowMain.add(
                InlineKeyboardButton.builder()
                .text("Записи")
                .callbackData("LIST_CLIENT_RECORD_TO_MASTER")
                .build()
        );

//        rowSecond.add(
//                InlineKeyboardButton.builder()
//                        .text("Удалить профиль")
//                        .callbackData("ANSWER_DELETE_MASTER")
//                        .build()
//        );
        return InlineKeyboardMarkup.builder()
                .keyboardRow(rowMain)
                .keyboardRow(rowSecond)
                .build();
    }
}