package com.bot.eyelashes.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Commands {

    INFO("/info");

    private final String command;

}
