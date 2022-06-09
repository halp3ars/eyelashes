package com.bot.eyelashes.repository;

import com.bot.eyelashes.model.entity.Master;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MasterRepository extends JpaRepository<Master, Long> {

    List<Master> findByActivity(String activity);

}
