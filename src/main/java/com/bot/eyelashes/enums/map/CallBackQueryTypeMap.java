package com.bot.eyelashes.enums.map;

import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.handler.callbackquery.impl.CallbackMasterImpl;
import com.bot.eyelashes.handler.callbackquery.impl.CallbackMenuImpl;
import com.bot.eyelashes.handler.callbackquery.impl.CallbackClientImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class CallBackQueryTypeMap {

    private static final Map<String, Callback> CALLBACK_MAP = new HashMap<>();

    public CallBackQueryTypeMap(@Qualifier("CallbackTypeOfActivity") Callback callbackActivity,@Qualifier("CallbackRecordMenuImpl") Callback callbackRecordMenu,@Qualifier("CallbackRecordToMasterImpl") Callback callbackRecordToMaster) {
        CALLBACK_MAP.put("MASTER", new CallbackMasterImpl());
        CALLBACK_MAP.put("MENU", new CallbackMenuImpl());
        CALLBACK_MAP.put("CLIENT", new CallbackClientImpl());
        CALLBACK_MAP.put("NAILS", callbackActivity);
        CALLBACK_MAP.put("EYEBROWS", callbackActivity);
        CALLBACK_MAP.put("EYELASHES", callbackActivity);
        CALLBACK_MAP.put("SET_MASTER", callbackRecordMenu);
    }


    public static Set<String> getCallbackKeySet() {
        return CALLBACK_MAP.keySet();
    }

    public Callback getCallback(String keyCallback) {
        return CALLBACK_MAP.get(keyCallback);
    }
}
