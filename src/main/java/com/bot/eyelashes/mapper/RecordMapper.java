package com.bot.eyelashes.mapper;


import com.bot.eyelashes.model.dto.RecordToMasterDto;
import com.bot.eyelashes.model.entity.RecordToMaster;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface RecordMapper {

    @Mapping(source = "clientId",target = "clientId")
    RecordToMaster toEntity(RecordToMasterDto recordToMasterDto);
    RecordToMasterDto toDto(RecordToMaster recordToMaster);
}
