package com.bot.eyelashes.model.dto;

import lombok.Data;

@Data
public class ScheduleDto {

    private boolean monday;
    private boolean tuesday;
    private boolean wednesday;
    private boolean thursday;
    private boolean friday;
    private boolean sunday;
    private boolean saturday;
    private Long telegramId;
    private int timeFrom;
    private int timeTo;
}
