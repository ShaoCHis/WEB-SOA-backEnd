package com.soa.schedule.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @ program: demo
 * @ description: doctor
 * @ author: ShenBo
 * @ date: 2021-11-19 21:52:25
 */
@Entity
@Getter
@Setter
public class Doctor {
    @Id
    private String Id;
    private String name;
    private String introduction;
    private Integer age;
    private String title;
    private Integer cost;
    private String hospital;
    private String department;
}
