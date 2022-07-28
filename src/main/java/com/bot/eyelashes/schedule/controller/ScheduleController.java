package com.bot.eyelashes.schedule.controller;

import com.bot.eyelashes.model.dto.Schedule2Dto;
import com.bot.eyelashes.model.dto.ScheduleDto;
import com.bot.eyelashes.model.entity.PeriodOfWork;
import com.bot.eyelashes.model.entity.Schedule2;
import com.bot.eyelashes.schedule.service.ScheduleService;
import com.bot.eyelashes.service.Bot;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final Bot bot;
    public static Long masterId;

    @PostMapping(value = "/schedule", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Message setScheduleMaster(@RequestBody ScheduleDto scheduleDto) throws TelegramApiException {
        scheduleService.saveSchedule(scheduleDto);
        return bot.execute(scheduleService.sendMessageForAuth());
    }

    @GetMapping(value = "/get")
    public String getOkMessage(){
        return "ok";
    }

    @PostMapping(value = "/Schedule")
    public Message setSchedule2(@RequestBody Schedule2Dto schedule2Dto) throws TelegramApiException {
        scheduleService.saveSchedule2(schedule2Dto);
        return bot.execute(scheduleService.sendMessageForAuth());
    }

    @GetMapping("/schedule/master")
    public Long getMasterId() {
        return ScheduleService.masterId;
    }

    @GetMapping("/day")
    public Schedule2 getSchedule2() {
        Schedule2 schedule2 = new Schedule2();
        List<PeriodOfWork> periodOfWorkList = new ArrayList<>();
        PeriodOfWork periodOfWork = new PeriodOfWork();
        periodOfWork.setDay("Суббота");
        periodOfWork.setFirstIntervalFrom(12);
        periodOfWorkList.add(new PeriodOfWork());
        periodOfWorkList.add(periodOfWork);

        schedule2.setTelegramId(222222L);
        schedule2.setPeriodOfWorks(periodOfWorkList);

        return schedule2;
    }
}
