package com.bot.eyelashes.cache;

import com.bot.eyelashes.mapper.RecordMapper;
import com.bot.eyelashes.model.dto.RecordToMasterDto;
import com.bot.eyelashes.model.entity.RecordToMaster;
import com.bot.eyelashes.repository.RecordToMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class ReplaceRecordMasterDataCache {
    private final RecordMapper recordMapper;
    private final RecordToMasterRepository recordToMasterRepository;

    private final Map<Long, RecordToMasterDto> recordToMasterData = new ConcurrentHashMap<>();

    public void setRecordToMasterData(Long userId, RecordToMasterDto recordToMasterDto) {
        recordToMasterData.put(userId, recordToMasterDto);
    }

    public RecordToMasterDto getRecordToMasterData(Long userId) {
        return recordToMasterData.get(userId);
    }

    @Transactional
    public void saveRecord(RecordToMasterDto recordToMasterDto) {
        RecordToMaster recordToMaster = recordMapper.toEntity(recordToMasterDto);
        recordToMasterRepository.save(recordToMaster);
    }

    public RecordToMasterDto setToDto(RecordToMaster recordToMaster) {
        return recordMapper.toDto(recordToMaster);
    }

    public  void deleteRecordByMasterId(long masterId, long clientId, String activity) {
        recordToMasterRepository.deleteByMasterIdAndClientIdAndActivity(masterId, clientId, activity);
    }
}
