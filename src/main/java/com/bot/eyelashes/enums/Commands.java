package com.bot.eyelashes.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Commands {

    INFO("/info"),
    CLIENTS("/clients"),
    MASTERS("/masters");

    private final String command;

}