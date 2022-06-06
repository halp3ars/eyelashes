package com.bot.eyelashes.handler.impl;

import com.bot.eyelashes.handler.Handle;
import com.bot.eyelashes.map.TypeOfActivity;
import com.bot.eyelashes.repository.MasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RequiredArgsConstructor
public class HandleTypeOfActivityImpl implements Handle {

    private final MasterRepository masterRepository;

    @Override
    public SendMessage getMessage(Message message) {
        return SendMessage.builder()
                .chatId(message.getChatId()
                        .toString())
                .replyMarkup(createInlineKeyboard())
                .text("Мастера")
                .build();
    }

    @Override
    public InlineKeyboardMarkup createInlineKeyboard() {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        TypeOfActivity typeOfActivity = new TypeOfActivity();
        for (int buttonsIndex = 0; buttonsIndex < masterRepository.findByActivity(typeOfActivity.getCommand("EYEBROWS")).size();buttonsIndex++){
            buttons.add(Arrays.asList(
                    InlineKeyboardButton.builder()
                            .text(masterRepository.findByActivity("Брови").get(buttonsIndex).getName())
                            .callbackData("DAS")
                            .build()
                    ));}
        inlineKeyboardMarkup.setKeyboard(buttons);
        return inlineKeyboardMarkup;
    }
}
