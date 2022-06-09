package com.bot.eyelashes.mapper;

import com.bot.eyelashes.model.dto.MasterDto;
import com.bot.eyelashes.model.entity.Master;
import org.mapstruct.Mapper;

@Mapper
public interface MasterMapper {
    Master toEntity(MasterDto masterDto);
}
