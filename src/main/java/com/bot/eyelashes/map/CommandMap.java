package com.bot.eyelashes.map;

import com.bot.eyelashes.handler.Handle;
import com.bot.eyelashes.handler.impl.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CommandMap {
    private static final Map<String, Handle> COMMAND_MAP = new HashMap<>();

    public CommandMap() {
        COMMAND_MAP.put("/info", new HandleInfoImpl());
        COMMAND_MAP.put("/start", new HandleStartImpl());
        COMMAND_MAP.put("/menu", new HandleMainMenuImpl());
        COMMAND_MAP.put("/client", new HandleClientImpl());
        COMMAND_MAP.put("/master", new HandleMasterImpl());
    }

    public Handle getCommand(String keyCommand) {
        return COMMAND_MAP.get(keyCommand);
    }
    public static Set<String> getCommandKeySet() {
        return COMMAND_MAP.keySet();
    }

}
