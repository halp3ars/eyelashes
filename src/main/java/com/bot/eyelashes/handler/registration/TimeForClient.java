package com.bot.eyelashes.handler.registration;

import com.bot.eyelashes.model.entity.RecordToMaster;
import com.bot.eyelashes.model.entity.Schedule;
import com.bot.eyelashes.repository.RecordToMasterRepository;
import com.bot.eyelashes.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class TimeForClient {

    private final RecordToMasterRepository recordToMasterRepository;
    private final ScheduleRepository scheduleRepository;

    public List getWorkTime(Long telegramId, String day, Long clientId) {
        Schedule scheduleByTelegramId = scheduleRepository.findByTelegramId(telegramId);
        List<RecordToMaster> recordToMaster = recordToMasterRepository.findByMasterId(telegramId);
        List<Integer> workHours = new ArrayList<>();
        IntStream.range(scheduleByTelegramId.getTimeFrom(), scheduleByTelegramId.getTimeTo())
                .forEach(workHours::add);
        List<Integer> reservedTime = new ArrayList<>();
        recordToMaster.stream()
                .filter(record -> record.getClientId()
                        .equals(clientId) & record.getDay()
                        .equals(day))
                .forEach(r -> reservedTime.add(r.getTime()));
        recordToMaster.stream()
                .filter(record -> record.getDay()
                        .equals(day))
                .forEach(r -> reservedTime.add(r.getTime()));

        workHours.removeAll(reservedTime);
        return workHours;
    }


}
