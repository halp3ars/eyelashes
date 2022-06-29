package com.bot.eyelashes.mapper;

import com.bot.eyelashes.model.dto.ScheduleDto;
import com.bot.eyelashes.model.entity.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ScheduleMapper {

    @Mapping(source = "masterId",target = "masterId")
    Schedule toEntity(ScheduleDto scheduleDto);

    @Mapping(source = "masterId",target = "masterId")
    ScheduleDto toDto(Schedule schedule);

}
