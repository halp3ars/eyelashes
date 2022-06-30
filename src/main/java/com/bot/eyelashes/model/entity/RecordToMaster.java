package com.bot.eyelashes.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "record_to_master")
@Getter
@Setter
public class RecordToMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "master_id")
    private Long masterId;
    @Column(name = "client_id")
    private Long clientId;
    @Column(name = "day")
    private String day;
    @Column(name = "time")
    private Integer time;
    @Column(name = "activity")
    private String activity;

}
