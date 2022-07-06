package com.bot.eyelashes.cache;

import com.bot.eyelashes.enums.BotState;
import com.bot.eyelashes.enums.ClientBotState;
import com.bot.eyelashes.mapper.MasterMapper;
import com.bot.eyelashes.mapper.ScheduleMapper;
import com.bot.eyelashes.model.dto.MasterDto;
import com.bot.eyelashes.model.dto.ScheduleDto;
import com.bot.eyelashes.model.entity.Master;
import com.bot.eyelashes.model.entity.Schedule;
import com.bot.eyelashes.repository.MasterRepository;
import com.bot.eyelashes.repository.ScheduleRepository;
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
    private Map<Long, ScheduleDto> scheduleData = new ConcurrentHashMap<>();
    private final MasterRepository masterRepository;
    private final MasterMapper masterMapper;
    private final ScheduleMapper scheduleMapper;
    private final ScheduleRepository scheduleRepository;

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


    public ScheduleDto getUserScheduleData(Long userId){
        ScheduleDto scheduleDto = scheduleData.get(userId);
        if (scheduleDto == null) {
            scheduleDto = new ScheduleDto();
        }
        return scheduleDto;
    }

    public void saveUserScheduleData(Long userId, ScheduleDto scheduleDto) {
        scheduleData.put(userId, scheduleDto);
    }


    public void setMasterInDb(MasterDto masterDto) {
        Master master = masterMapper.toEntity(masterDto);
        masterRepository.save(master);
    }
    public void setScheduleInDb(ScheduleDto scheduleDto) {
        Schedule schedule = scheduleMapper.toEntity(scheduleDto);
        scheduleRepository.save(schedule);
    }
}
