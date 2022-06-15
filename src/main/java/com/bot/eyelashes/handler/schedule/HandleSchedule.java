package com.bot.eyelashes.handler.schedule;

import com.bot.eyelashes.enums.StateSchedule;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface HandleSchedule {
     SendMessage getMessage(Message message);
     StateSchedule getStateSchedule();
}
