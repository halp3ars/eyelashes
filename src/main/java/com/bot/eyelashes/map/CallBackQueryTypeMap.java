package com.bot.eyelashes.map;

import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.handler.callbackquery.impl.CallBackMenuImpl;
import com.bot.eyelashes.handler.callbackquery.impl.CallbackClientImpl;
import com.bot.eyelashes.handler.callbackquery.impl.CallbackMasterImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class CallBackQueryTypeMap {

    private static final Map<String, Callback> CALLBACK_MAP = new HashMap<>();

    public CallBackQueryTypeMap(@Qualifier("CallbackTypeOfActivity") Callback callback) {
        CALLBACK_MAP.put("MENU", new CallBackMenuImpl());
        CALLBACK_MAP.put("MASTER", new CallbackMasterImpl());
        CALLBACK_MAP.put("CLIENT", new CallbackClientImpl());
        CALLBACK_MAP.put("NAILS", callback);
        CALLBACK_MAP.put("EYEBROWS", callback);
        CALLBACK_MAP.put("EYELASHES", callback);
    }


    public static Set<String> getCallbackKeySet() {
        return CALLBACK_MAP.keySet();
    }

    public Callback getCallback(String keyCallback) {
        return CALLBACK_MAP.get(keyCallback);
    }
}
