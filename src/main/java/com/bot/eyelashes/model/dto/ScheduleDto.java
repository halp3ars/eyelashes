package com.bot.eyelashes.model.dto;

import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDate;

@Data
public class ScheduleDto {

    private boolean monday;
    private boolean tuesday;
    private boolean wednesday;
    private boolean thursday;
    private boolean friday;
    private Long telegramId;
    private int timeFrom;
    private int timeTo;
}
