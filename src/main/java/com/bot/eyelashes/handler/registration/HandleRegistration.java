package com.bot.eyelashes.handler.registration;

import com.bot.eyelashes.enums.BotState;
import com.bot.eyelashes.enums.ClientBotState;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public interface HandleRegistration {
    SendMessage getMessage(Message message);

    BotState getHandleName();

    ClientBotState getHandleClientName();
}
