package com.bot.eyelashes.enums.map;

import com.bot.eyelashes.handler.Handle;
import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.handler.impl.*;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashMap;
import java.util.Map;

public class CommandMap {
    private static final Map<String, Handle> COMMAND_MAP = new HashMap<>();

    public CommandMap() {
        COMMAND_MAP.put("/info", new HandleInfoImpl());
        COMMAND_MAP.put("/start", new HandleStartImpl());
        COMMAND_MAP.put("/menu", new HandleMainMenuImpl());
    }

    public Handle getCommand(String keyCommand) {
        return COMMAND_MAP.get(keyCommand);
    }

}
