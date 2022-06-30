package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.handler.impl.HandleScheduleClientImpl;
import com.bot.eyelashes.mapper.ScheduleMapper;
import com.bot.eyelashes.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;


@RequiredArgsConstructor
@Service("CallbackScheduleClientImpl")
public class CallbackScheduleClientImpl implements Callback {

    private final ScheduleRepository scheduleRepository;

    private final ScheduleMapper scheduleMapper;


    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        HandleScheduleClientImpl handleScheduleClient = new HandleScheduleClientImpl(scheduleMapper,scheduleRepository);
        return SendMessage.builder().replyMarkup(handleScheduleClient.createInlineKeyboard()).text("Выберите день недели на который вы хотите записаться").chatId(callbackQuery.getMessage().getChatId().toString()).build();
    }
}
