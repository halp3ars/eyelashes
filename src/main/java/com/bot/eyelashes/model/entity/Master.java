package com.bot.eyelashes.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

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
    @NotNull
    private String surname;
    @Column(name = "name")
    private String name;
    @Column(name = "address")
    private String address;
    @Column(name = "middlename")
    private String middleName;
    @Column(name = "phonenumber")
    private String phoneNumber;
    @Column(name = "activity")
    private String activity;
    @Column(name = "telegram_id")
    private Long telegramId;
    @Column(name = "telegram_nickname")
    private String telegramNick;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Activity> activities;

    @OneToMany
    private Set<Schedule> schedule;

}
