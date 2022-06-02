package com.bot.eyelashes.enums;

import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.handler.callbackquery.impl.CallbackInfoImpl;
import com.bot.eyelashes.handler.callbackquery.impl.CallbackStartImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CallBackQueryTypeMap {
    private final Map<String, Callback> CALLBACK_MAP = new HashMap<>();

    public void getCallbackType() {
        CALLBACK_MAP.put("INFO", new CallbackInfoImpl());
        CALLBACK_MAP.put("START", new CallbackStartImpl());
    }

    public Set<String> getCallbackKeySet() {
        return CALLBACK_MAP.keySet();
    }

    public Callback getCallback(String keyCallback) {
        return CALLBACK_MAP.get(keyCallback);
    }
}
