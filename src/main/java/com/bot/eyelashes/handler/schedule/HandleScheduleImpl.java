package com.bot.eyelashes.handler.schedule;

import com.bot.eyelashes.cache.ScheduleDataCacheImpl;
import com.bot.eyelashes.enums.StateSchedule;
import com.bot.eyelashes.model.dto.ScheduleDto;
import com.bot.eyelashes.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class HandleScheduleImpl implements HandleSchedule {
    private final ScheduleDataCacheImpl scheduleDataCache;
    private final MessageService messageService;
    private int countDay;

    @Override
    public SendMessage getMessage(Message message) {
        if (scheduleDataCache.getMessageCurrentState(message.getFrom()
                        .getId())
                .equals(StateSchedule.COUNT_DAY)) {
            scheduleDataCache.setUsersCurrentBotState(message.getFrom()
                    .getId(), StateSchedule.ASK_DATA_TIME);
        }
        return processInputMessage(message);
    }


    @Override
    public StateSchedule getStateSchedule() {
        return StateSchedule.COUNT_DAY;
    }

    private SendMessage processInputMessage(Message message) {
        Long userId = message.getFrom()
                .getId();
        Long chatId = message.getChatId();
        StateSchedule stateSchedule = scheduleDataCache.getUsersCurrentBotState(userId);
        SendMessage replyMessage = null;
        ScheduleDto scheduleDto = new ScheduleDto();
        if (stateSchedule.equals(StateSchedule.COUNT_DAY)) {
            replyMessage = messageService.getReplyMessage(chatId, "Введите кол-во дней которое вы работаете: ");

            scheduleDataCache.setUsersCurrentBotState(userId, StateSchedule.ASK_DATA_TIME);
        }
        if (stateSchedule.equals(StateSchedule.ASK_DATA_TIME)) {
            countDay = Integer.parseInt(message.getText());
            replyMessage = messageService.getReplyMessage(chatId, "Укажите даты работы в формате 29.02.2022 (ДД.ММ.ГГГГ) 12:00(ВРЕМЯ)");
            scheduleDataCache.setUsersCurrentBotState(userId, StateSchedule.FILLING_DATA_TIME);
        }
        if (stateSchedule.equals(StateSchedule.FILLING_DATA_TIME)) {
            String dataTimeWork = message.getText();
            log.info("dataTimeWork " + dataTimeWork);
            String[] dataTime = dataTimeWork.split(" ");
            log.info("array dataTime = " + Arrays.toString(dataTime));

            --countDay;
            if (countDay == 0)
                replyMessage = SendMessage.builder()
                        .text("Ваше расписание записано.")
                        .chatId(chatId.toString())
                        .replyMarkup(keyboard())
                        .build();

            if (countDay != 0)
                replyMessage = messageService.getReplyMessage(chatId, "записан");

            scheduleDataCache.saveScheduleForMaster(scheduleDto);
        }

        return replyMessage;
    }

    private InlineKeyboardMarkup keyboard() {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(List.of(
                InlineKeyboardButton.builder()
                        .text("Главное меню")
                        .callbackData("MENU")
                        .build()
        ));

        return InlineKeyboardMarkup.builder()
                .keyboard(buttons)
                .build();
    }
}