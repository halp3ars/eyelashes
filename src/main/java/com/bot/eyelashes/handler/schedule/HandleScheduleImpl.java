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
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class HandleScheduleImpl implements HandleSchedule {
    private Map<LocalDate, String> workingHours = new HashMap<>();
    private final ScheduleDataCacheImpl scheduleDataCache;
    private final MessageService messageService;
    private int countDay;

    @Override
    public SendMessage getMessage(Message message) {
        if (scheduleDataCache.getUsersCurrentBotState(message.getFrom().getId()).equals(StateSchedule.FILLING_SCHEDULE)) {
            scheduleDataCache.setUsersCurrentBotState(message.getFrom().getId(), StateSchedule.COUNT_DAY);
        }
        return processInputMessage(message);
    }



    @Override
    public StateSchedule getStateSchedule() {
        return StateSchedule.FILLING_SCHEDULE;
    }

    private SendMessage processInputMessage(Message message) {
        String answer = message.getText();
        Long userId = message.getFrom().getId();
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
            replyMessage = messageService.getReplyMessage(chatId, "Введите свое расписание в формате\nДата время");
            scheduleDataCache.setUsersCurrentBotState(userId, StateSchedule.FILLING_DATA_TIME);
        }
        if (stateSchedule.equals(StateSchedule.FILLING_DATA_TIME)) {
            String dataTimeWork = message.getText();
            log.info("dataTimeWork " + dataTimeWork);
            String[] dataTime = dataTimeWork.split(" ");
            log.info("array dataTime = " + Arrays.toString(dataTime));
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            scheduleDto.setDate(LocalDate.parse(dataTime[0], dateTimeFormatter));
            scheduleDto.setTime(dataTime[1]);
            scheduleDto.setTelegramId(userId);
            countDay--;
            if (countDay == 0)
                scheduleDataCache.setUsersCurrentBotState(userId, StateSchedule.FILLED_SCHEDULE);
            replyMessage = messageService.getReplyMessage(chatId, "записан");
            scheduleDataCache.saveScheduleForMaster(scheduleDto);
        }

        if (stateSchedule.equals(StateSchedule.FILLED_SCHEDULE)) {
            replyMessage = messageService.getReplyMessage(userId, "все ок");
        }


        return replyMessage;
    }

    private InlineKeyboardMarkup keyboard() {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(List.of(
                InlineKeyboardButton.builder()
                        .text("Список клиентов")
                        .callbackData("LIST_CLIENT")
                        .build()
        ));

        return InlineKeyboardMarkup.builder()
                .keyboard(buttons)
                .build();
    }
}
