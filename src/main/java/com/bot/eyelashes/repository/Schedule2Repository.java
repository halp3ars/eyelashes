package com.bot.eyelashes.repository;

import com.bot.eyelashes.model.entity.Schedule2;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Schedule2Repository extends JpaRepository<Schedule2, Long> {


    Schedule2 findByTelegramId(Long telegramId);


}
