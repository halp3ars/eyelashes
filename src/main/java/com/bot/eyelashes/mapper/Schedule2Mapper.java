package com.bot.eyelashes.mapper;

import com.bot.eyelashes.model.dto.Schedule2Dto;
import com.bot.eyelashes.model.entity.Schedule2;
import org.mapstruct.Mapper;

@Mapper
public interface Schedule2Mapper {
    Schedule2 toEntity(Schedule2Dto schedule2Dto);
}
