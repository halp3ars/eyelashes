package com.bot.eyelashes.repository;

import com.bot.eyelashes.model.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
