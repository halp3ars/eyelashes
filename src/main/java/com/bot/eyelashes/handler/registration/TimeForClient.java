package com.bot.eyelashes.handler.registration;

import com.bot.eyelashes.model.entity.RecordToMaster;
import com.bot.eyelashes.model.entity.Schedule;
import com.bot.eyelashes.repository.RecordToMasterRepository;
import com.bot.eyelashes.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class TimeForClient {

    private final RecordToMasterRepository recordToMasterRepository;
    private final ScheduleRepository scheduleRepository;

    public Set getWorkTime(Long telegramId){
        Schedule scheduleByTelegramId = scheduleRepository.findByTelegramId(telegramId);
        List<RecordToMaster> scheduleByMasterId = recordToMasterRepository.findByMasterId(telegramId);
        int amountHours = scheduleByTelegramId.getTimeTo() - scheduleByTelegramId.getTimeFrom();
        Set<Integer> workHours = new HashSet<>();
        IntStream.range(scheduleByTelegramId.getTimeFrom(), scheduleByTelegramId.getTimeTo()).forEach(workHours::add);
        scheduleByMasterId.forEach(time -> workHours.remove(time.getTime()));
        return workHours;
    }


}
