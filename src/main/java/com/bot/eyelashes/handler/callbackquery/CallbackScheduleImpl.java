package com.bot.eyelashes.handler.callbackquery;

import com.bot.eyelashes.cache.ScheduleDataCacheImpl;
import com.bot.eyelashes.enums.StateSchedule;
import com.bot.eyelashes.model.dto.ScheduleDto;
import com.bot.eyelashes.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

@RequiredArgsConstructor
public class CallbackScheduleImpl implements CallbackSchedule {
    private final ScheduleDataCacheImpl scheduleDataCache;
    private final MessageService messageService;

    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        if (scheduleDataCache.getUsersCurrentBotState(callbackQuery.getMessage().getFrom().getId()).equals(StateSchedule.FILLING_SCHEDULE)) {
            scheduleDataCache.setUsersCurrentBotState(callbackQuery.getMessage().getFrom().getId(), StateSchedule.COUNT_DAY);
        }
        return processUsersInput(callbackQuery.getMessage());
    }

    private SendMessage processUsersInput(Message inputMessage) {
        String usersAnswer = inputMessage.getText();
        Long userId = inputMessage.getFrom()
                .getId();
        Long chatId = inputMessage.getChatId();

        ScheduleDto masterDto = scheduleDataCache.getUserProfileData(userId);
        StateSchedule stateSchedule = scheduleDataCache.getUsersCurrentBotState(userId);

        SendMessage replyToMaster = null;
        if (stateSchedule.equals(StateSchedule.COUNT_DAY)) {
            replyToMaster = messageService.getReplyMessage(chatId, "Введите количество рабочих дней");
            scheduleDataCache.setUsersCurrentBotState(userId, StateSchedule.ASK_DATA_TIME);
        }

        return replyToMaster;
    }

    @Override
    public SendMessage getMessage(Message message) {
        return null;
    }

    @Override
    public StateSchedule getHandleName() {
        return StateSchedule.FILLING_SCHEDULE;
    }
}
