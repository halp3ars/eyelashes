package com.bot.eyelashes.handler.callbackquery;

import com.bot.eyelashes.model.entity.PeriodOfWork;
import com.bot.eyelashes.model.entity.Schedule2;
import com.bot.eyelashes.repository.MasterRepository;
import com.bot.eyelashes.repository.PeriodOfWorkRepository;
import com.bot.eyelashes.repository.Schedule2Repository;
import com.bot.eyelashes.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service("DeleteProfileMasterCallbackImpl")
@RequiredArgsConstructor
@Slf4j
public class DeleteProfileMasterCallbackImpl implements Callback {
    private final MasterRepository masterRepository;
    private final ScheduleRepository scheduleRepository;
    private final Schedule2Repository schedule2Repository;

    @Transactional
    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        long chatId = callbackQuery.getMessage().getChatId();
        deleteMaster(chatId);
        deleteNewSchedule(chatId);
        deleteScheduleMaster(chatId);

        return SendMessage.builder()
                .text("Ваш профиль удален.\nВернитесь в главное меню.")
                .chatId(chatId)
                .replyMarkup(createKeyboardForDeleteMaster())
                .build();
    }

    @Transactional
    public void deleteMaster(long masterId) {
        masterRepository.deleteByTelegramId(masterId);
    }

    @Transactional
    public void deleteNewSchedule(long masterId) {
        schedule2Repository.deleteByTelegramId(masterId);
    }

    @Transactional
    public void deleteScheduleMaster(long masterId) {
        scheduleRepository.deleteByTelegramId(masterId);
    }

    private InlineKeyboardMarkup createKeyboardForDeleteMaster() {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(Collections.singletonList(
                InlineKeyboardButton.builder()
                        .text("Меню")
                .callbackData("MENU")
                .build()));

        return InlineKeyboardMarkup.builder()
                .keyboard(buttons)
                .build();
    }
}
