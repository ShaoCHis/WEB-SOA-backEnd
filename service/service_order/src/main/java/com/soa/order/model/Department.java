package com.soa.order.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.soa.order.views.DepartmentWithDoctors;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * @ program: demo
 * @ description: Department
 * @ author: ShenBo
 * @ date: 2021-11-18 14:40:40
 */
@Entity
@Data
@Table(name="department")
public class Department {
    @Id
    private String Id;
    private String name;
    private String introduction;

    @JsonBackReference
    @ManyToMany(cascade = CascadeType.ALL,targetEntity=Hospital.class,mappedBy="departments") //让Hospital维护外键表
    private List<Hospital> hospitals;

    @JsonBackReference
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
