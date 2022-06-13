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

    public CallBackQueryTypeMap(@Qualifier("CallbackTypeOfActivity") Callback callbackActivity,@Qualifier("CallbackRecordMenuImpl") Callback callbackRecordMenu,@Qualifier("CallbackRecordToMasterImpl") Callback callbackRecordToMaster, @Qualifier("CallbackCheckRecordImpl") Callback callbackCheckRecord,
    @Qualifier("CallbackRecordImpl") Callback callbackRecordImpl,@Qualifier("CallbackDeclineImpl") Callback callbackDecline) {
        CALLBACK_MAP.put("MASTER", new CallbackMasterImpl());
        CALLBACK_MAP.put("MENU", new CallbackMenuImpl());
        CALLBACK_MAP.put("CLIENT", new CallbackClientImpl());
        CALLBACK_MAP.put("NAILS", callbackActivity);
        CALLBACK_MAP.put("EYEBROWS", callbackActivity);
        CALLBACK_MAP.put("EYELASHES", callbackActivity);
        CALLBACK_MAP.put("SET_MASTER", callbackRecordMenu);
        CALLBACK_MAP.put("RECORD", callbackRecordImpl);
        CALLBACK_MAP.put("CHECK_RECORD", callbackCheckRecord);
        CALLBACK_MAP.put("DECLINE_RECORD", callbackDecline);

    }


    public static Set<String> getCallbackKeySet() {
        return CALLBACK_MAP.keySet();
    }

    public Callback getCallback(String keyCallback) {
        return CALLBACK_MAP.get(keyCallback);
    }
}
