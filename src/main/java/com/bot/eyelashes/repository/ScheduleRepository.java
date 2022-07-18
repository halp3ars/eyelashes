package com.bot.eyelashes.repository;

import com.bot.eyelashes.model.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long>{

    Schedule findByTelegramId(Long telegramId);
    Optional<Schedule> findByTelegramId(long telegramId);
    void deleteByTelegramId(Long masterId);

}
