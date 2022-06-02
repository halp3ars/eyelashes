package com.bot.eyelashes.enums;

import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.handler.callbackquery.impl.CallbackStartImpl;
import com.bot.eyelashes.handler.callbackquery.impl.CallbackInfoImpl;
import com.bot.eyelashes.handler.impl.HandleMainMenuImpl;

public enum CallbackQueryType {
    //    REGISTRATION(new C ), AUTH, MENU, INFO, START,MENU_CLIENT, MENU_MASTER;
    INFO(new CallbackInfoImpl()), START(new CallbackStartImpl()), MASTER(new CallbackInfoImpl()), CLIENT(new CallbackStartImpl()),
    MENU(new CallbackInfoImpl());

    private final Callback CALLBACK;

    CallbackQueryType(Callback CALLBACK) {
        this.CALLBACK = CALLBACK;
    }

    public static CallbackQueryType getTypeCommand(String callback) {
        for (CallbackQueryType type : CallbackQueryType.values()) {
            if (type.name()
                    .equals(callback))
                return type;
        }
        throw new RuntimeException("not found");
    }

    public Callback getCALLBACK() {
        return CALLBACK;
    }
}
