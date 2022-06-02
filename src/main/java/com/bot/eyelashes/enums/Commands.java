package com.bot.eyelashes.enums;


import com.bot.eyelashes.handler.Handle;
import com.bot.eyelashes.handler.impl.HandleMainMenuImpl;
import com.bot.eyelashes.handler.impl.HandleStartImpl;
import com.bot.eyelashes.handler.impl.HandleInfoImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Commands {
    INFO("/info", new HandleInfoImpl()),
    START("/start",new HandleStartImpl()),
    MENU("/menu", new HandleMainMenuImpl());

    private final String COMMAND;
    private final Handle HANDLE;

    public static Commands getTypeCommand(String command) {
        for (Commands type : Commands.values()) {
            if (type.COMMAND.equals(command))
                return type;
        }
        throw new RuntimeException("not found");
    }



}