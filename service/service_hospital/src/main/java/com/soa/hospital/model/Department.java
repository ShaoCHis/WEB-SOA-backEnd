package com.soa.hospital.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @ program: demo
 * @ description: Department
 * @ author: ShenBo
 * @ date: 2021-11-18 14:40:40
 */
@Entity
@Getter
@Setter
@Table(name="department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String Id;
    private String name;
    private String introduction;
}
