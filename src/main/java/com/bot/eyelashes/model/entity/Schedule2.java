package com.bot.eyelashes.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
public class Schedule2 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<PeriodOfWork> periodOfWorks;
    @Column(name = "telegramId")
    private Long telegramId;
}