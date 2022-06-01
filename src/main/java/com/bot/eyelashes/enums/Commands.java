package com.bot.eyelashes.enums;


import com.bot.eyelashes.handler.Handle;
import com.bot.eyelashes.handler.impl.HandleMasterImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Commands {
    INFO("/info", new HandleMasterImpl()),
    CLIENTS("/clients", new HandleMasterImpl()),
    MASTERS("/masters", new HandleMasterImpl());

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