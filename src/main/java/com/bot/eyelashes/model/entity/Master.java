package com.bot.eyelashes.model.entity;

import com.bot.eyelashes.enums.TypeOfActivity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "master")
public class Master {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "surname")
    private String surname;
    @Column(name = "name")
    private String name;
    @Column(name = "middlename")
    private String middlename;
    @Column(name = "phonenumber")
    private String phoneNumber;
    @Column(name = "address")
    private String address;

    @Column(name = "activity_type")
    private String activity;

    @Column(name = "telegram_id")
    private Long telegramId;

}
