package com.bot.eyelashes.handler.impl;

import com.bot.eyelashes.handler.Handle;
import com.bot.eyelashes.enums.map.TypeOfActivity;
import com.bot.eyelashes.model.entity.Master;
import com.bot.eyelashes.repository.MasterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Slf4j
public class HandleTypeOfActivityImpl implements Handle {

    private final MasterRepository masterRepository;

    @Override
    public SendMessage getMessage(Update update) {
        return SendMessage.builder()
                .chatId(update.getMessage()
                        .getChatId()
                        .toString())
                .replyMarkup(createInlineKeyboardWithCallback(update.getCallbackQuery()))
                .text("Мастера")
                .build();
    }

    @Override
    public InlineKeyboardMarkup createInlineKeyboard() {
        return null;
    }

    public InlineKeyboardMarkup createInlineKeyboardWithCallback(CallbackQuery callbackQuery) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        TypeOfActivity typeOfActivity = new TypeOfActivity();
        masterRepository.findByActivity(typeOfActivity.getCommand(callbackQuery.getData()))
                .forEach(master -> buttons.add(List.of(InlineKeyboardButton.builder()
                        .text(master.getName() + " " + master.getSurname() + " Адрес " + master.getAddress())
                        .callbackData("SET_MASTER/" + callbackQuery.getData() + "/" + master.getId())
                        .build())));
        inlineKeyboardMarkup.setKeyboard(buttons);
        return inlineKeyboardMarkup;
    }
}
