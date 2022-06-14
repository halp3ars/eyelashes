package com.bot.eyelashes.enums.map;

import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.handler.callbackquery.impl.CallbackMasterImpl;
import com.bot.eyelashes.handler.callbackquery.impl.CallbackMenuImpl;
import com.bot.eyelashes.handler.callbackquery.impl.CallbackClientImpl;
import com.bot.eyelashes.handler.callbackquery.impl.CallbackRecordImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class CallBackQueryTypeMap {

    private static final Map<String, Callback> CALLBACK_MAP = new HashMap<>();

    public CallBackQueryTypeMap(@Qualifier("CallbackTypeOfActivity") Callback callbackActivity,@Qualifier("CallbackRecordMenuImpl") Callback callbackRecordMenu, @Qualifier("CallbackCheckRecordImpl") Callback callbackCheckRecord,
    @Qualifier("CallbackDeclineImpl") Callback callbackDecline, @Qualifier("CallbackClientImpl") Callback callbackClient) {
        CALLBACK_MAP.put("MASTER", new CallbackMasterImpl());
        CALLBACK_MAP.put("MENU", new CallbackMenuImpl());
        CALLBACK_MAP.put("CLIENT", callbackClient);
        CALLBACK_MAP.put("NAILS", callbackActivity);
        CALLBACK_MAP.put("EYEBROWS", callbackActivity);
        CALLBACK_MAP.put("EYELASHES", callbackActivity);
        CALLBACK_MAP.put("SET_MASTER", callbackRecordMenu);
        CALLBACK_MAP.put("CHECK_RECORD", callbackCheckRecord);
        CALLBACK_MAP.put("DECLINE_RECORD", callbackDecline);
//        CALLBACK_MAP.put("RECORD", new CallbackRecordImpl());

    }


    public static Set<String> getCallbackKeySet() {
        return CALLBACK_MAP.keySet();
    }

    public Callback getCallback(String keyCallback) {
        return CALLBACK_MAP.get(keyCallback);
    }
}
