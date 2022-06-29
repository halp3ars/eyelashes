package com.bot.eyelashes.model.dto;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class MasterDto {
    private String phone;
    private String activity;
    private Long telegramId;
    private String name;
    private String surname;
    private String middleName;
    private String telegramNick;

    public String toString() {
        return "ФИО: " + this.getSurname() + " " + this.getName() + " " + this.getMiddleName() + " " + "\nНомер телефона: " + this.getPhone() + "\n Вид услуг: " + this.getActivity() +"\nОтправьте готово для продолжения";
    }
}
