package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.model.entity.Master;
import com.bot.eyelashes.repository.MasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("ProfileMasterCallback")
@RequiredArgsConstructor
public class ProfileMasterCallback implements Callback {
    private final MasterRepository masterRepository;

    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        Optional<Master> master = masterRepository.findMasterByTelegramId(callbackQuery.getMessage()
                .getChatId());

        return SendMessage.builder()
                .text(
                        "Фамилия: " + master.get().getSurname() +
                                "\nИмя: " + master.get().getName() +
                                "\nАдрес: " + master.get().getAddress() +
                                "\nВид услуг: " + master.get().getActivity() +
                                "\nНомер телефона: " + master.get().getPhoneNumber())
                .chatId(callbackQuery.getMessage().getChatId().toString())
                .replyMarkup(keyboardProfile())
                .build();
    }

    public InlineKeyboardMarkup keyboardProfile() {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(List.of(
                InlineKeyboardButton.builder()
                        .text("Изменить профиль")
                        .callbackData("REPLACE_PROFILE")
                        .build(),
                InlineKeyboardButton.builder()
                        .text("Меню")
                        .callbackData("MASTER")
                        .build()
        ));

        return InlineKeyboardMarkup.builder()
                .keyboard(buttons)
                .build();
    }
}
