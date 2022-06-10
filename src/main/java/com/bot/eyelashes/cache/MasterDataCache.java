package com.bot.eyelashes.cache;

import com.bot.eyelashes.enums.BotState;
import com.bot.eyelashes.mapper.MasterMapper;
import com.bot.eyelashes.model.dto.MasterDto;
import com.bot.eyelashes.model.entity.Master;
import com.bot.eyelashes.repository.MasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class MasterDataCache implements DataCache {
    private Map<Long, BotState> mastersBotStates = new ConcurrentHashMap<>();
    private Map<Long, MasterDto> mastersData = new ConcurrentHashMap<>();
    private  final MasterRepository masterRepository;
    private final MasterMapper masterMapper;
    @Override
    public void setUsersCurrentBotState(Long userId, BotState botState) {
        mastersBotStates.put(userId, botState);
    }

    @Override
    public BotState getUsersCurrentBotState(Long userId) {
        BotState botState = mastersBotStates.get(userId);
        if (botState == null) {
            botState = BotState.FILLING_PROFILE;
        }

        return botState;
    }

    @Override
    public MasterDto getUserProfileData(Long userId) {
        MasterDto masterDto = mastersData.get(userId);
        if (masterDto == null) {
            masterDto = new MasterDto();
        }
        return masterDto;
    }

    @Override
    public void saveUserProfileData(Long userId, MasterDto masterDto) {
        mastersData.put(userId, masterDto);
    }

    public void setMasterInDb(MasterDto masterDto) {
        Master master = masterMapper.toEntity(masterDto);
        masterRepository.save(master);
    }
}
