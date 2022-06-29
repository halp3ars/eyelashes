package com.bot.eyelashes.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class Schedule {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long masterId;
    private boolean monday;
    private boolean tuesday;
    private boolean wednesday;
    private boolean thursday;
    private boolean friday;
    private Long telegramId;

}
