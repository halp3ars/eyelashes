package com.bot.eyelashes.model.dto;

import com.bot.eyelashes.model.entity.PeriodOfWork;
import lombok.Getter;
import lombok.Setter;


import java.util.Set;

@Setter
@Getter
public class Schedule2Dto {
    private Long id;
    private Set<PeriodOfWork> periodOfWorks;
    private Long telegramId;
}
