package com.bot.eyelashes.validation;

import com.bot.eyelashes.model.dto.ScheduleDto;

public class DayMasterValidation {
    public static boolean isEmptyDaysForMasterRegistration(ScheduleDto scheduleDto) {
        return !scheduleDto.isMonday() && !scheduleDto.isFriday() && !scheduleDto.isThursday() &&
                !scheduleDto.isWednesday() && !scheduleDto.isTuesday() && !scheduleDto.isSaturday() &&
                !scheduleDto.isSunday();
    }
}
