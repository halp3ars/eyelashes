package com.bot.eyelashes.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class RecordToMasterDto {

    private Long masterId;
    private Long clientId;
    private String day;
    private String time;

}
