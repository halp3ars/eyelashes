package com.bot.eyelashes.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity(name = "period_of_work")
public class PeriodOfWork {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "first_interval_from")
    private Integer firstIntervalFrom;
    @Column(name = "first_interval_to")
    private Integer firstIntervalTo;
    @Column(name = "second_interval_from")
    private Integer secondIntervalFrom;
    @Column(name = "second_interval_to")
    private Integer secondIntervalTo;
    @Column(name = "third_interval_from")
    private Integer thirdIntervalFrom;
    @Column(name = "third_interval_to")
    private Integer thirdIntervalTo;
    @Column(name = "fourth_interval_from")
    private Integer fourthIntervalFrom;
    @Column(name = "fourth_interval_to")
    private Integer fourthIntervalTo;
    @Column(name = "day")
    private String day;

}