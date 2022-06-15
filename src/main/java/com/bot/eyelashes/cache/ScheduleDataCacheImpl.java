package com.bot.eyelashes.cache;

import com.bot.eyelashes.enums.StateSchedule;
import com.bot.eyelashes.mapper.ScheduleMapper;
import com.bot.eyelashes.model.dto.MasterDto;
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
public class ScheduleDataCacheImpl {
    private Map<Long, StateSchedule> mastersStateSchedule = new ConcurrentHashMap<>();
    private final Map<Long, ScheduleDto> scheduleData = new HashMap<>();

    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;

    public void setUsersCurrentBotState(Long userId, StateSchedule stateSchedule) {
        mastersStateSchedule.put(userId, stateSchedule);
    }


    public StateSchedule getUsersCurrentBotState(Long userId) {
        StateSchedule stateSchedule = mastersStateSchedule.get(userId);
        if (stateSchedule == null)
            stateSchedule = StateSchedule.FILLING_SCHEDULE;

        return stateSchedule;
    }

    public StateSchedule getMessageCurrentState(Long userId) {
        StateSchedule stateSchedule = mastersStateSchedule.get(userId);
        if (stateSchedule == null)
            stateSchedule = StateSchedule.COUNT_DAY;

        return stateSchedule;
    }

    public ScheduleDto getUserProfileData(Long userId) {
        return scheduleData.get(userId);
    }


    public void saveScheduleForMaster(ScheduleDto scheduleDto) {
        Schedule schedule = scheduleMapper.toEntity(scheduleDto);
        scheduleRepository.save(schedule);
    }
}
