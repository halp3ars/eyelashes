package com.bot.eyelashes.mapper;

import com.bot.eyelashes.model.dto.MasterDto;
import com.bot.eyelashes.model.entity.Master;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface MasterMapper {
    @Mapping(source = "phone", target = "phoneNumber")
    @Mapping(source = "activity", target = "activity")
    Master toEntity(MasterDto masterDto);
}
