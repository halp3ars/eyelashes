package com.bot.eyelashes.service;

import com.bot.eyelashes.enums.DayOfWeek;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class HashMapDayOfWeekModeService {
    private final Map<Long, DayOfWeek> originalDay = new HashMap<>();
    private final Map<Long, DayOfWeek> targetDay = new HashMap<>();

    public DayOfWeek getOriginalDay(long chatId) {
        return originalDay.getOrDefault(chatId, DayOfWeek.MONDAY);
    }

    public DayOfWeek getTargetDay(long chatId) {
        return targetDay.getOrDefault(chatId, DayOfWeek.MONDAY);
    }

    public void setOriginalDay(long chatId, DayOfWeek dayOfWeek) {
        originalDay.put(chatId, dayOfWeek);
    }

    public void setTargetDay(long chatId, DayOfWeek dayOfWeek) {
        targetDay.put(chatId, dayOfWeek);
    }

}
