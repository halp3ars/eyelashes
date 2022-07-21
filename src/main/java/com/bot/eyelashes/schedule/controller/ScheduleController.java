package com.bot.eyelashes.schedule.controller;

import com.bot.eyelashes.model.dto.ScheduleDto;
import com.bot.eyelashes.schedule.service.ScheduleService;
import com.bot.eyelashes.service.Bot;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final Bot bot;

    @PostMapping(value = "/schedule", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Message setScheduleMaster(@RequestBody ScheduleDto scheduleDto) throws TelegramApiException {
        scheduleService.saveSchedule(scheduleDto);
//        masterDataCache.setUsersCurrentBotState(649681305L, BotState.REGISTERED);
        return bot.execute(scheduleService.sendMessageForAuth());
    }





}
