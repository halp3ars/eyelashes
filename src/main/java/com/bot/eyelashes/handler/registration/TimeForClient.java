package com.bot.eyelashes.handler.registration;

import com.bot.eyelashes.cache.ClientDataCache;
import com.bot.eyelashes.model.dto.RecordToMasterDto;
import com.bot.eyelashes.model.entity.PeriodOfWork;
import com.bot.eyelashes.model.entity.RecordToMaster;
import com.bot.eyelashes.model.entity.Schedule;
import com.bot.eyelashes.model.entity.Schedule2;
import com.bot.eyelashes.repository.RecordToMasterRepository;
import com.bot.eyelashes.repository.Schedule2Repository;
import com.bot.eyelashes.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class TimeForClient {

    private final RecordToMasterRepository recordToMasterRepository;
    private final Schedule2Repository schedule2Repository;
    private final ClientDataCache clientDataCache;

    public List getWorkTime(Long clientId) {
        RecordToMasterDto recordData = clientDataCache.getRecordData(clientId);
        List<RecordToMaster> recordToMaster = recordToMasterRepository.findByMasterId(recordData.getMasterId());
        List<Integer> workHours = new ArrayList<>();
        String day = recordData.getDay();
        Schedule2 schedule = schedule2Repository.findByTelegramId(recordData.getMasterId());
        PeriodOfWork periodOfWorks = schedule.getPeriodOfWorks()
                .stream()
                .filter(n -> n.getDay()
                        .equals(day))
                .toList()
                .get(0);
        IntStream.range(periodOfWorks.getFirstIntervalFrom() - 1, periodOfWorks.getFirstIntervalTo() + 1).forEach(workHours::add);
        IntStream.range(periodOfWorks.getSecondIntervalFrom(), periodOfWorks.getSecondIntervalTo() + 1).forEach(workHours::add);;
        IntStream.range(periodOfWorks.getThirdIntervalFrom(), periodOfWorks.getThirdIntervalTo() + 1).forEach(workHours::add);;
        IntStream.range(periodOfWorks.getFourthIntervalFrom(), periodOfWorks.getFourthIntervalTo() + 1).forEach(workHours::add);;
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
