package com.bot.eyelashes.enums.map;

import com.bot.eyelashes.handler.Handle;
import com.bot.eyelashes.handler.impl.HandleInfoImpl;
import com.bot.eyelashes.handler.impl.HandleMainMenuImpl;
import com.bot.eyelashes.handler.impl.HandleStartImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommandMap {
    private static final Map<String, Handle> COMMAND_MAP = new HashMap<>();

    public CommandMap(@Qualifier("HandleCommandMasterImpl") Handle handleMaster) {
        COMMAND_MAP.put("/info", new HandleInfoImpl());
        COMMAND_MAP.put("/start", new HandleStartImpl());
        COMMAND_MAP.put("/menu", new HandleMainMenuImpl());
        COMMAND_MAP.put("/master", handleMaster);
    }

    public Handle getCommand(String keyCommand) {
        return COMMAND_MAP.get(keyCommand);
    }
}
