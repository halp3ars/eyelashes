package com.bot.eyelashes.enums.map;


import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.handler.impl.HandleInfoImpl;
import com.bot.eyelashes.handler.impl.HandleMainMenuImpl;
import com.bot.eyelashes.handler.impl.HandleStartImpl;
import com.bot.eyelashes.model.dto.ScheduleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleMap {

    private static final Map<String, Boolean> SCHEDULE_MAP = new HashMap<>();

    public ScheduleMap(ScheduleDto scheduleDto) {
        SCHEDULE_MAP.put("Понедельник", scheduleDto.isMonday());
        SCHEDULE_MAP.put("Вторник", scheduleDto.isTuesday());
        SCHEDULE_MAP.put("Среда", scheduleDto.isWednesday());
        SCHEDULE_MAP.put("Четверг", scheduleDto.isThursday());
        SCHEDULE_MAP.put("Пятница", scheduleDto.isFriday());
    }

    public Set getScheduleDayMap() {
        SCHEDULE_MAP.values().stream().allMatch(t -> t);
        Set<String> daySet = SCHEDULE_MAP.entrySet().stream()
                .filter(Map.Entry::getValue).map(Map.Entry::getKey)
                .collect(Collectors.toSet());
        return daySet;
    }


}
