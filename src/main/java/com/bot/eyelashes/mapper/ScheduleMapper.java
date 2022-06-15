package com.bot.eyelashes.mapper;

import com.bot.eyelashes.model.dto.ScheduleDto;
import com.bot.eyelashes.model.entity.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ScheduleMapper {
    @Mapping(source = "telegramId", target = "telegramId")
    @Mapping(source = "date", target = "date")
    @Mapping(source = "time", target = "time")
    Schedule toEntity(ScheduleDto scheduleDto);
}
