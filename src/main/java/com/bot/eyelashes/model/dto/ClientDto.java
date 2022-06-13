package com.bot.eyelashes.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDto {

        private String name;
        private String middleName;
        private String surname;
        private String phoneNumber;
        private Long telegramId;

        public String toString() {
                return this.getSurname()  +  " "  + this.getName() +  " "  + this.getMiddleName() + "\nНомер телефона =" + this.getPhoneNumber() + "\nОтправьте готово для продолжения";
        }

}
