package com.bot.eyelashes.repository;

import com.bot.eyelashes.model.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByTelegramId(Long telegramId);

}
