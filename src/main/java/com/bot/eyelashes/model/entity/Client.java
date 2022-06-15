package com.bot.eyelashes.model.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "surname")
    private String surname;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "telegram_ID")
    private String telegramId;

}
