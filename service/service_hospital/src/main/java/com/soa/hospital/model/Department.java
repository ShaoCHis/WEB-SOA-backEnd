package com.soa.hospital.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.soa.hospital.views.DepartmentWithDoctors;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private String Id;
    private String name;
    private String introduction;
    @JsonBackReference
    @ManyToMany(cascade = CascadeType.ALL,targetEntity=Hospital.class,mappedBy="departments") //让Hospital维护外键表
    private List<Hospital> hospitals;

    @OneToMany(mappedBy = "department",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private Set<Doctor> doctorSet;

    public Department(DepartmentWithDoctors departmentWithDoctors){
        this.name=departmentWithDoctors.getName();
        this.introduction=departmentWithDoctors.getIntroduction();
        this.Id=departmentWithDoctors.getId();
    }

    public Department() {

    }
}
