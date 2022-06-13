package com.bot.eyelashes.repository;

import com.bot.eyelashes.model.entity.Master;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MasterRepository extends JpaRepository<Master, Long> {

    Optional<Master> findByTelegramId(Long telegramId);

    List<Master> findByActivity(String activity);
}
