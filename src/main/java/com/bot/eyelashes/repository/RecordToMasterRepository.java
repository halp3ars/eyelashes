package com.bot.eyelashes.repository;

import com.bot.eyelashes.model.entity.RecordToMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecordToMasterRepository extends JpaRepository<RecordToMaster, Long> {


    Optional<RecordToMaster> findByClientId(Long id);

    Optional<RecordToMaster> findByClientIdAndActivity(Long clientId, String activity);

    void deleteByClientId(Long clientId);


}