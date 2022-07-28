package com.bot.eyelashes.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "activity")
@Setter
@Getter
public class Activity {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
}
