package com.bot.eyelashes.mapper;

import com.bot.eyelashes.model.dto.ScheduleDto;
import com.bot.eyelashes.model.entity.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ScheduleMapper {

    @Mapping(source = "saturday",target = "saturday")
    Schedule toEntity(ScheduleDto scheduleDto);

    @Mapping(source = "saturday",target = "saturday")
    ScheduleDto toDto(Schedule schedule);

}
