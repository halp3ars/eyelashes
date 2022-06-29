package com.bot.eyelashes.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ScheduleDto {
    private Long masterId;
    private boolean monday;
    private boolean tuesday;
    private boolean wednesday;
    private boolean thursday;
    private boolean friday;
}
