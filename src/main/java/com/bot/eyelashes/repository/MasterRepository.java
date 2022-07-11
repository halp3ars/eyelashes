package com.bot.eyelashes.repository;

import com.bot.eyelashes.model.entity.Master;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface MasterRepository extends JpaRepository<Master, Long> {

    Optional<Master> findMasterByTelegramId(Long telegramId);
    List<Master> findAllByTelegramId(Long telegramId);
    List<Master> findByActivity(String activity);
    Boolean existsByTelegramId(Long id);
}
