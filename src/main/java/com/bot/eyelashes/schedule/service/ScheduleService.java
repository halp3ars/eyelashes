package com.bot.eyelashes.schedule.service;

import com.bot.eyelashes.cache.MasterDataCache;
import com.bot.eyelashes.handler.BotStateContext;
import com.bot.eyelashes.mapper.ScheduleMapper;
import com.bot.eyelashes.model.dto.MasterDto;
import com.bot.eyelashes.model.dto.ScheduleDto;
import com.bot.eyelashes.model.entity.Schedule;
import com.bot.eyelashes.model.entity.Schedule2;
import com.bot.eyelashes.repository.Schedule2Repository;
import com.bot.eyelashes.repository.ScheduleRepository;
import com.bot.eyelashes.service.Bot;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;
    private final MasterDataCache masterDataCache;
    public static long masterId;
    private final Schedule2Repository schedule2Repository;


    @Transactional
    public void saveSchedule(ScheduleDto scheduleDto) {
        Schedule schedule = scheduleMapper.toEntity(scheduleDto);
        schedule.setTelegramId(masterId);
        if (scheduleRepository.findByTelegramId(masterId).isPresent())
            scheduleRepository.deleteByTelegramId(masterId);

        scheduleRepository.save(schedule);
    }

    public SendMessage sendMessageForAuth() {
        MasterDto masterDto = masterDataCache.getUserProfileData(masterId);
        masterDataCache.setMasterInDb(masterDto);
        Bot.masterRegistration = false;

        return SendMessage.builder()
                .text("Вы зарегистрированы.\nПерейдите в главное меню")
                .replyMarkup(createFinalButton())
                .chatId(masterId)
                .build();
    }

    private InlineKeyboardMarkup createFinalButton() {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(List.of(InlineKeyboardButton.builder()
                .text("Меню")
                .callbackData("MASTER")
                .build()));
        return InlineKeyboardMarkup.builder()
                .keyboard(buttons)
                .build();
    }

    public void saveSchedule2(Schedule2 schedule2) {
        schedule2Repository.save(schedule2);
    }
}
