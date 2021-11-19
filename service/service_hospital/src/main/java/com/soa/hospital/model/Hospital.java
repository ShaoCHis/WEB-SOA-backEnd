package com.soa.hospital.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * @ program: demo
 * @ description: Hospital
 * @ author: ShenBo
 * @ date: 2021-11-16 18:35:42
 */
@Entity
@Getter
@Setter
@Table(name="hospital")
public class Hospital {
    @Id
    private String Id;
    private String password;
    private String code;
    private String name;
    private String introduction;
    private String image;
    private String url;
    private Integer level;
    private String location;
    private String notice;
    private Integer status;

    @OneToMany(fetch= FetchType.EAGER)
    @JoinTable(name="own",joinColumns={@JoinColumn(name="hospital_ID")}
            ,inverseJoinColumns={@JoinColumn(name="department_ID")})
    private List<Department> departments;
}
