package com.bot.eyelashes.repository;

import com.bot.eyelashes.model.entity.Master;
import com.bot.eyelashes.model.entity.RecordToMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecordToMasterRepository extends JpaRepository<RecordToMaster, Long> {


    List<RecordToMaster> findByClientId(Long id);

    Optional<RecordToMaster> findRecordToMasterByActivityAndClientId(String activity,Long clientId);

    List<RecordToMaster> findByMasterId(Long masterId);
    void deleteByClientId(Long clientId);


}