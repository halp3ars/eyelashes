package com.bot.eyelashes.enums.map;


import com.bot.eyelashes.model.dto.ScheduleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleClientMap {

    private static final Map<String, Boolean> SCHEDULE_MAP = new ConcurrentHashMap<>();

    public ScheduleClientMap(ScheduleDto scheduleDto) {
        SCHEDULE_MAP.put("Понедельник", scheduleDto.isMonday());
        SCHEDULE_MAP.put("Вторник", scheduleDto.isTuesday());
        SCHEDULE_MAP.put("Среда", scheduleDto.isWednesday());
        SCHEDULE_MAP.put("Четверг", scheduleDto.isThursday());
        SCHEDULE_MAP.put("Пятница", scheduleDto.isFriday());
    }

    public List<String> getTrueDays() {
        SCHEDULE_MAP.values()
                .stream()
                .allMatch(t -> t);
        List<String> dayList = SCHEDULE_MAP.entrySet()
                .stream()
                .filter(Map.Entry::getValue)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        return dayList;
    }

}
