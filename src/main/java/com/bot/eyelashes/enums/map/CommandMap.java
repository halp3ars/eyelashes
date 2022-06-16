package com.bot.eyelashes.enums.map;

import com.bot.eyelashes.handler.Handle;
import com.bot.eyelashes.handler.impl.HandleInfoImpl;
import com.bot.eyelashes.handler.impl.HandleMainMenuImpl;
import com.bot.eyelashes.handler.impl.HandleStartImpl;

import java.util.HashMap;
import java.util.Map;

public class CommandMap {
    private static final Map<String, Handle> COMMAND_MAP = new HashMap<>();

    public CommandMap() {
        COMMAND_MAP.put("/info", new HandleInfoImpl());
        COMMAND_MAP.put("/start", new HandleStartImpl()); // TODO: Переделать информацию на /start сделав ее как информационый текст со списком комманд
        COMMAND_MAP.put("/menu", new HandleMainMenuImpl()); // TODO: Надпись: Кто по масти или Кто ты такой ЧОРТ ?

    }

    public Handle getCommand(String keyCommand) {
        return COMMAND_MAP.get(keyCommand);
    }

}
