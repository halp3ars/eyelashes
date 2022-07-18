package com.bot.eyelashes.cache;

import com.bot.eyelashes.enums.StateSchedule;
import com.bot.eyelashes.mapper.ScheduleMapper;
import com.bot.eyelashes.model.dto.ScheduleDto;
import com.bot.eyelashes.model.entity.Schedule;
import com.bot.eyelashes.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class ReplaceScheduleDataCache {
    private Map<Long, StateSchedule> mastersStateSchedule = new ConcurrentHashMap<>();
    private final Map<Long, ScheduleDto> scheduleData = new HashMap<>();

    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;

//    public void setUsersCurrentBotState(Long userId, StateSchedule stateSchedule) {
//        mastersStateSchedule.put(userId, stateSchedule);
//    }

    public void setScheduleData(Long userId, ScheduleDto scheduleDto) {
        scheduleData.put(userId, scheduleDto);
    }


    public StateSchedule getUsersCurrentBotState(Long userId) {
        return mastersStateSchedule.getOrDefault(userId, StateSchedule.FILLING_SCHEDULE);
    }

    public ScheduleDto getUserProfileData(Long userId) {
        return scheduleData.get(userId);
    }


    public void saveScheduleForMaster(ScheduleDto scheduleDto) {
        Schedule schedule = scheduleMapper.toEntity(scheduleDto);
        scheduleRepository.save(schedule);
    }
}
