package com.bot.eyelashes.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ScheduleDto {
    private Long telegramId;
    private LocalDate date;
    private String time;
}
