package com.bot.eyelashes.model.dto;

import lombok.Data;

@Data
public class RecordToMasterDto {

    private Long masterId;
    private Long clientId;
    private String day;
    private String time;
    private String activity;

}
