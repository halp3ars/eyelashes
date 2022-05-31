package com.bot.eyelashes.mapper;

import com.bot.eyelashes.model.dto.ClientDto;
import com.bot.eyelashes.model.entity.Client;
import org.mapstruct.Mapper;

@Mapper
public interface ClientMapper {

    Client toEntity(ClientDto clientDto);

}
