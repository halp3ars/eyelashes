package com.bot.eyelashes.repository;

import com.bot.eyelashes.model.entity.RecordToMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecordToMasterRepository extends JpaRepository<RecordToMaster, Long> {

    Optional<RecordToMaster> findByDay(String day);
    Optional<RecordToMaster> findByDayAndMasterId(String day, long masterId);
    Optional<RecordToMaster> findByClientIdAndActivity(Long clientId,String activity);
    List<RecordToMaster> findByMasterId(Long masterId);
    void deleteByClientIdAndActivity(Long clientId,String activity);


}