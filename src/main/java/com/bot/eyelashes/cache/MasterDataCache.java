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
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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
        return mastersBotStates.getOrDefault(userId, BotState.FILLING_PROFILE);
    }

    @Override
    public MasterDto getUserProfileData(Long userId) {
        return mastersData.getOrDefault(userId, new MasterDto());
    }



    @Override
    public void saveUserProfileData(Long userId, MasterDto masterDto) {
        mastersData.put(userId, masterDto);
    }


    public ScheduleDto getUserScheduleData(Long userId){
        return scheduleData.getOrDefault(userId, new ScheduleDto());
    }

    public void saveUserScheduleData(Long userId, ScheduleDto scheduleDto) {
        scheduleData.put(userId, scheduleDto);
    }


    @Transactional
    public void setMasterInDb(MasterDto masterDto) {
        Optional<Master> masterByTelegramId = masterRepository.findMasterByTelegramId(masterDto.getTelegramId());
        Master master = masterMapper.toEntity(masterDto);
        masterByTelegramId.ifPresent(value -> masterRepository.deleteByTelegramId(value.getTelegramId()));
        masterRepository.save(master);
    }
    @Transactional
    public void setScheduleInDb(ScheduleDto scheduleDto) {
        long telegramId = scheduleDto.getTelegramId();
        Optional<Schedule> masterSchedule = scheduleRepository.findByTelegramId(telegramId);
        Schedule schedule = scheduleMapper.toEntity(scheduleDto);
        masterSchedule.ifPresent(schedule1 -> scheduleRepository.deleteByTelegramId(telegramId));
        scheduleRepository.save(schedule);
    }
}
