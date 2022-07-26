package com.bot.eyelashes.handler;

import com.bot.eyelashes.cache.MasterDataCache;
import com.bot.eyelashes.enums.BotState;
import com.bot.eyelashes.handler.callbackquery.impl.CallbackMasterRegistrationImpl;
import com.bot.eyelashes.repository.MasterRepository;
import com.bot.eyelashes.service.Bot;
import com.bot.eyelashes.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Service("HandleCommandMasterImpl")
@RequiredArgsConstructor
public class HandleCommandMasterImpl implements Handle {
    private final MasterRepository masterRepository;

    @Override
    public SendMessage getMessage(Update update) {
        if (masterRepository.existsByTelegramId(update.getMessage().getChatId())) {
            return SendMessage.builder().text("Выберите действие")
                    .chatId(update.getMessage().getChatId().toString())
                    .replyMarkup(createInlineKeyboard()).build();
        }

        return SendMessage.builder()
                .chatId(update.getMessage().getChatId().toString())
                .text("Вы не зарегистрированы.\nДля регистрации нажмите на кнопку")
                .replyMarkup(keyboard()).build();
    }

    @Override
    public InlineKeyboardMarkup createInlineKeyboard() {
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

        rowSecond.add(
                InlineKeyboardButton.builder()
                        .text("Удалить профиль")
                        .callbackData("ANSWER_DELETE_MASTER")
                        .build()
        );
        rowSecond.add(
                InlineKeyboardButton.builder()
                        .text("Меню")
                        .callbackData("MENU")
                        .build()
        );
        return InlineKeyboardMarkup.builder()
                .keyboardRow(rowMain)
                .keyboardRow(rowSecond)
                .build();
    }

    private InlineKeyboardMarkup keyboard() {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(Collections.singletonList(
                InlineKeyboardButton.builder().text("Регистрация").callbackData("MASTER").build()
        ));

        return InlineKeyboardMarkup.builder().keyboard(buttons).build();
    }
}
