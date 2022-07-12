package com.bot.eyelashes.enums.map;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ScheduleMasterMap {
    private static final Map<String, String> SCHEDULE_MASTER_MAP = new ConcurrentHashMap<>();

    public ScheduleMasterMap() {
        SCHEDULE_MASTER_MAP.put("MONDAY","Понедельник");
        SCHEDULE_MASTER_MAP.put("TUESDAY","Вторник");
        SCHEDULE_MASTER_MAP.put("WEDNESDAY","Среда");
        SCHEDULE_MASTER_MAP.put("THURSDAY","Четверг");
        SCHEDULE_MASTER_MAP.put("FRIDAY","Пятница");
    }

    public Map getMap(){
        return SCHEDULE_MASTER_MAP;
    }

    public String getDay(String keyDay) {
        return SCHEDULE_MASTER_MAP.get(keyDay);
    }
}
