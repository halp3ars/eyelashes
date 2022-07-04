package com.bot.eyelashes.handler.registration;


import com.bot.eyelashes.mapper.ScheduleMapper;
import com.bot.eyelashes.model.dto.ScheduleDto;
import com.bot.eyelashes.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
public class ScheduleForClient {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;

    public ScheduleDto getMasterDays(Long telegramId){
        return scheduleMapper.toDto(scheduleRepository.findByTelegramId(telegramId));
    }
}
