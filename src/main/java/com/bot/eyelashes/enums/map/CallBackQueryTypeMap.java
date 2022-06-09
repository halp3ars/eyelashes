package com.bot.eyelashes.enums.map;

import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.handler.callbackquery.impl.CallBackMenuImpl;
import com.bot.eyelashes.handler.callbackquery.impl.CallbackMasterImpl;

import java.util.HashMap;
import java.util.Map;


public class CallBackQueryTypeMap {
    private  final Map<String, Callback> CALLBACK_MAP = new HashMap<>();

    public CallBackQueryTypeMap() {
        CALLBACK_MAP.put("MENU", new CallBackMenuImpl());
        CALLBACK_MAP.put("MASTER", new CallbackMasterImpl());
    }



    public Callback getCallback(String keyCallback) {
        return CALLBACK_MAP.get(keyCallback);
    }
}
