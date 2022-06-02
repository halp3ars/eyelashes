package com.bot.eyelashes.enums;

import com.bot.eyelashes.handler.Handle;
import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.handler.callbackquery.impl.CallBackStartImpl;
import com.bot.eyelashes.handler.callbackquery.impl.CallbackInfoImpl;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public enum CallbackQueryType {
//    REGISTRATION(new C ), AUTH, MENU, INFO, START,MENU_CLIENT, MENU_MASTER;
    INFO(new CallbackInfoImpl()), START(new CallBackStartImpl());

    private final Callback CALLBACK;

    CallbackQueryType(Callback CALLBACK) {
        this.CALLBACK = CALLBACK;
    }

    public static Callback getTypeCommand(String callback) {
        for (Callback type: CallbackQueryType.values()) {
            if (type..equals(command))
                return type;
        }
        throw new RuntimeException("not found");
    }

}
