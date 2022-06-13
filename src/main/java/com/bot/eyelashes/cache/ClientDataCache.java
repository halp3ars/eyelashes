package com.bot.eyelashes.cache;

import com.bot.eyelashes.enums.BotState;
import com.bot.eyelashes.enums.ClientBotState;
import com.bot.eyelashes.mapper.ClientMapper;
import com.bot.eyelashes.mapper.RecordMapper;
import com.bot.eyelashes.model.dto.ClientDto;
import com.bot.eyelashes.model.dto.MasterDto;
import com.bot.eyelashes.model.dto.RecordToMasterDto;
import com.bot.eyelashes.model.entity.Client;
import com.bot.eyelashes.model.entity.RecordToMaster;
import com.bot.eyelashes.repository.ClientRepository;
import com.bot.eyelashes.repository.RecordToMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class ClientDataCache{



    private Map<Long,ClientBotState> ClientState = new ConcurrentHashMap<>();
    private Map<Long, ClientDto> ClientData = new ConcurrentHashMap<>();
    private Map<Long, RecordToMasterDto> RecordData = new ConcurrentHashMap<>();
    private final ClientRepository clientRepository;
    private final RecordToMasterRepository recordRepository;
    private final ClientMapper clientMapper;
    private final RecordMapper recordMapper;


    public void setClientBotState(Long userId, ClientBotState clientBotState){
        ClientState.put(userId,clientBotState);
    }


    public ClientBotState getClientBotState(Long userId){
        ClientBotState clientBotState = ClientState.get(userId);
        if(clientBotState == null){
            clientBotState = ClientBotState.FILLING_CLIENT_PROFILE;
        }

        return clientBotState;
    }


    public ClientDto getClientProfileData(Long userId) {
        ClientDto clientDto = ClientData.get(userId);
        if (clientDto == null) {
            clientDto = new ClientDto();
        }
        return clientDto;
    }

    public void saveClientProfileData(Long userId, ClientDto clientDto){
        ClientData.put(userId,clientDto);
    }

    public RecordToMasterDto getRecordData(Long userId){
        RecordToMasterDto recordToMasterDto = RecordData.get(userId);
        if(recordToMasterDto == null){
            recordToMasterDto = new RecordToMasterDto();
        }
        return recordToMasterDto;
    }



    public void saveRecordData(Long userId, RecordToMasterDto recordToMasterDto){
        RecordData.put(userId,recordToMasterDto);
    }

    public void setClientIntoDb(ClientDto clientDto){
        Client client = clientMapper.toEntity(clientDto);
        clientRepository.save(client);
    }

    public void setClientRecord(RecordToMasterDto recordToMasterDto){
        RecordToMaster recordToMaster = recordMapper.toEntity(recordToMasterDto);
        recordRepository.save(recordToMaster);
    }

}
