package com.bot.eyelashes.handler.registration;


import com.bot.eyelashes.model.entity.RecordToMaster;
import com.bot.eyelashes.repository.RecordToMasterRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class CheckerForRecord {

    private final RecordToMasterRepository recordToMasterRepository;

    Optional<RecordToMaster> getRecord(Long telegramId,String activity){
        return recordToMasterRepository.findByClientIdAndActivity(telegramId, activity);
    }
}
