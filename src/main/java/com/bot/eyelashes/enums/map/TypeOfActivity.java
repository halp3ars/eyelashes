package com.bot.eyelashes.enums.map;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TypeOfActivity {
    private static final Map<String, String> TYPE_OF_ACTIVITY = new HashMap<>();

    public TypeOfActivity() {
        TYPE_OF_ACTIVITY.put("EYEBROWS", "Брови");
        TYPE_OF_ACTIVITY.put("EYELASHES", "Ресницы");
        TYPE_OF_ACTIVITY.put("NAILS", "Ногти");
    }

    public String getCommand(String keyCommand) {
        return TYPE_OF_ACTIVITY.get(keyCommand);
    }

    public String getKey(String key) {
        String keyMap = "";
        for (var entry : TYPE_OF_ACTIVITY.entrySet()) {
            if (key.equals(entry.getKey()))
                keyMap = entry.getKey();
        }
        return keyMap;
    }

}
