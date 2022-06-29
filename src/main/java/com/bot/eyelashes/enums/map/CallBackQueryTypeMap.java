package com.bot.eyelashes.enums.map;

import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.handler.callbackquery.impl.CallbackInfoImpl;
import com.bot.eyelashes.handler.callbackquery.impl.CallbackMenuImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CallBackQueryTypeMap {

    private static final Map<String, Callback> CALLBACK_MAP = new HashMap<>();

    public CallBackQueryTypeMap(@Qualifier("CallbackTypeOfActivity") Callback callbackActivity,
                                @Qualifier("CallbackRecordMenuImpl") Callback callbackRecordMenu,
                                @Qualifier("CallbackCheckRecordImpl") Callback callbackCheckRecord,
                                @Qualifier("CallbackDeclineImpl") Callback callbackDecline,
                                @Qualifier("CallbackClientImpl") Callback callbackClient,
                                @Qualifier("CallbackService") Callback callbackService,
                                @Qualifier("CallbackScheduleClientImpl") Callback callbackScheduleClient) {
        CALLBACK_MAP.put("INFO", new CallbackInfoImpl());
        CALLBACK_MAP.put("MENU", new CallbackMenuImpl());
        CALLBACK_MAP.put("CLIENT", callbackClient);
        CALLBACK_MAP.put("NAILS", callbackActivity);
        CALLBACK_MAP.put("EYEBROWS", callbackActivity);
        CALLBACK_MAP.put("EYELASHES", callbackActivity);
        CALLBACK_MAP.put("SET_MASTER", callbackRecordMenu);
        CALLBACK_MAP.put("CHECK_RECORD", callbackCheckRecord);
        CALLBACK_MAP.put("DECLINE_RECORD", callbackDecline);
        CALLBACK_MAP.put("eyebrows", callbackService);
        CALLBACK_MAP.put("eyelashes", callbackService);
        CALLBACK_MAP.put("nails", callbackService);
        CALLBACK_MAP.put("SCHEDULE_CLIENT", callbackScheduleClient);
    }

    public Callback getCallback(String keyCallback) {
        return CALLBACK_MAP.get(keyCallback);
    }
}
