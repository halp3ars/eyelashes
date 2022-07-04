package com.bot.eyelashes.enums.map;

import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.handler.callbackquery.impl.CallbackClientImpl;
import com.bot.eyelashes.handler.callbackquery.impl.CallbackInfoImpl;
import com.bot.eyelashes.handler.callbackquery.impl.CallbackMenuImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CallBackQueryTypeMap {

    private final Map<String, Callback> callbackMap = new HashMap<>();

    public CallBackQueryTypeMap(@Qualifier("CallbackTypeOfActivity") Callback callbackActivity,
                                @Qualifier("CallbackRecordMenuImpl") Callback callbackRecordMenu,
                                @Qualifier("CallbackCheckRecordImpl") Callback callbackCheckRecord,
                                @Qualifier("CallbackDeclineImpl") Callback callbackDecline,
                                @Qualifier("CallbackService") Callback callbackService,
                                @Qualifier("CallbackScheduleClientImpl") Callback callbackScheduleClient,
                                @Qualifier("CallbackTimeClientImpl") Callback callbackTimeClient,
                                @Qualifier("CallbackDateClientImpl") Callback callbackDateClient,
                                @Qualifier("CallbackRecordImpl") Callback callbackRecord,
                                @Qualifier("CallbackChangeDateImpl") Callback callbackChangeDate) {
        callbackMap.put("INFO", new CallbackInfoImpl());
        callbackMap.put("MENU", new CallbackMenuImpl());
        callbackMap.put("CLIENT", new CallbackClientImpl());
        callbackMap.put("NAILS", callbackActivity);
        callbackMap.put("EYEBROWS", callbackActivity);
        callbackMap.put("EYELASHES", callbackActivity);
        callbackMap.put("SET_MASTER", callbackRecordMenu);
        callbackMap.put("CHECK_RECORD", callbackCheckRecord);
        callbackMap.put("DECLINE_RECORD", callbackDecline);
        callbackMap.put("eyebrows", callbackService);
        callbackMap.put("eyelashes", callbackService);
        callbackMap.put("nails", callbackService);
        callbackMap.put("SCHEDULE_CLIENT", callbackScheduleClient);
        callbackMap.put("DATE", callbackDateClient);
        callbackMap.put("TIME", callbackTimeClient);
        callbackMap.put("RECORD",callbackRecord);
        callbackMap.put("CHANGE_DATE",callbackChangeDate);
    }

    public Callback getCallback(String keyCallback) {
        return callbackMap.get(keyCallback);
    }
}
