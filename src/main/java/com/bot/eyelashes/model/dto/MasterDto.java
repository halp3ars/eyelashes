package com.bot.eyelashes.model.dto;

import com.bot.eyelashes.enums.TypeOfActivity;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class MasterDto {
    private String surname;
    private String name;
    private String middlename;
    private String phoneNumber;
    private String address;
    private String activity;
    private Long telegramId;

}
