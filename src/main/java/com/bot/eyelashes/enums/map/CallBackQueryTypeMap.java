package com.bot.eyelashes.enums.map;

import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.handler.callbackquery.ListClientRecordToMasterCallback;
import com.bot.eyelashes.handler.callbackquery.impl.CallbackClientImpl;
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
                                @Qualifier("CallbackTimeClientImpl") Callback callbackTimeClient,
                                @Qualifier("CallbackDateClientImpl") Callback callbackDateClient,
                                @Qualifier("CallbackRecordImpl") Callback callbackRecord,
                                @Qualifier("CallbackChangeDateImpl") Callback callbackChangeDate,
                                @Qualifier("CallbackMasterRegistrationImpl") Callback callbackMasterRegistration,
                                @Qualifier("CallbackMasterTimeFromImpl") Callback callbackMasterTimeFromImpl,
                                @Qualifier("CallbackMasterScheduleImpl") Callback callbackMasterScheduleImpl,
                                @Qualifier("CallbackMasterTimeToImpl") Callback callbackMasterTimeToImpl,
                                @Qualifier("CallbackMasterRegisteredImpl") Callback callbackMasterRegisteredImpl,
                                @Qualifier("MasterDayCallback") Callback callbackMasterDay,
                                @Qualifier("ListClientByDayCallback") Callback callbackListClientByDay
    ) {
        CALLBACK_MAP.put("INFO", new CallbackInfoImpl());
        CALLBACK_MAP.put("MENU", new CallbackMenuImpl());
        CALLBACK_MAP.put("CLIENT", new CallbackClientImpl());
        CALLBACK_MAP.put("NAILS", callbackActivity);
        CALLBACK_MAP.put("EYEBROWS", callbackActivity);
        CALLBACK_MAP.put("EYELASHES", callbackActivity);
        CALLBACK_MAP.put("SET_MASTER", callbackRecordMenu);
        CALLBACK_MAP.put("CHECK_RECORD", callbackCheckRecord);
        CALLBACK_MAP.put("DECLINE_RECORD", callbackDecline);
        CALLBACK_MAP.put("DATE", callbackDateClient);
        CALLBACK_MAP.put("TIME", callbackTimeClient);
        CALLBACK_MAP.put("RECORD", callbackRecord);
        CALLBACK_MAP.put("CHANGE_DATE", callbackChangeDate);
        CALLBACK_MAP.put("MASTER_ACTIVITY",callbackMasterScheduleImpl);
        CALLBACK_MAP.put("MASTER", callbackMasterRegistration);
        CALLBACK_MAP.put("MASTER_TIME", callbackMasterTimeFromImpl);
        CALLBACK_MAP.put("TIME_TO", callbackMasterTimeToImpl);
        CALLBACK_MAP.put("REGISTERED",callbackMasterRegisteredImpl);
        CALLBACK_MAP.put("MASTER_DAY",callbackMasterDay);
        CALLBACK_MAP.put("LIST_CLIENT_RECORD_TO_MASTER", new ListClientRecordToMasterCallback());
        CALLBACK_MAP.put("DAY_CLIENT_RECORD_TO_MASTER", callbackListClientByDay);
    }

    public Callback getCallback(String keyCallback) {
        return CALLBACK_MAP.get(keyCallback);
    }
}
