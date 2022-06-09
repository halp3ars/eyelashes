package com.bot.eyelashes.enums.map;

import com.bot.eyelashes.handler.registration.HandleRegistration;

import java.util.HashMap;
import java.util.Map;

public class Registration {
    private  Map<String, HandleRegistration> REGISTRATION_MAP = new HashMap<>();
//    private final MasterDataCache masterDataCache;
//    public Registration() {
//        REGISTRATION_MAP.put("FULL_NAME", new HandleFullNameImpl(masterDataCache));
//        REGISTRATION_MAP.put("ACTIVITY", new HandleActivityImpl());
//        REGISTRATION_MAP.put("CONTACT", new HandleContactImpl());
//    }

    public Registration(/*MasterDataCache masterDataCache*/) {
//        this.masterDataCache = masterDataCache;
//        REGISTRATION_MAP.put("FULL_NAME", new HandleFullNameImpl(masterDataCache));
//        REGISTRATION_MAP.put("ACTIVITY", new HandleActivityImpl());
//        REGISTRATION_MAP.put("CONTACT", new HandleContactImpl());
    }

    public HandleRegistration getHandleRegistration(String handle) {
        return REGISTRATION_MAP.get(handle);
    }


}
