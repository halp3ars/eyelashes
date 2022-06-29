package com.bot.eyelashes.enums.map;

import java.util.HashMap;
import java.util.Map;

public class ActivityId {
    private static final Map<String, Long> ACTIVITY_ID = new HashMap<>();

    public ActivityId() {
        ACTIVITY_ID.put("Брови", 1L);
        ACTIVITY_ID.put("Ресницы", 2L);
        ACTIVITY_ID.put("Ногти", 3L);
    }

    public Long getIdByName(String name) {
        return ACTIVITY_ID.get(name);
    }
}
