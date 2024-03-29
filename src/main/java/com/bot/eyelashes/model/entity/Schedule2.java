package com.bot.eyelashes.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.List;


@Entity
@Getter
@Setter
public class Schedule2 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<PeriodOfWork> periodOfWorks;
    @Column(name = "telegramId")
    private Long telegramId;


}

