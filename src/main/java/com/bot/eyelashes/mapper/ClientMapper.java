package com.bot.eyelashes.mapper;

import com.bot.eyelashes.model.dto.ClientDto;
import com.bot.eyelashes.model.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ClientMapper {


    @Mapping(source = "telegramId", target = "telegramId")
    Client toEntity(ClientDto clientDto);
}
