package com.soa.hospital.model;

import com.soa.hospital.views.HospitalInfo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @ManyToMany(targetEntity=Department.class)
    @JoinTable(name="own",
            joinColumns={@JoinColumn(name="hospital_ID",referencedColumnName="Id")}
            ,inverseJoinColumns={@JoinColumn(name="department_ID",referencedColumnName="Id")})
    private List<Department> departments;

    @OneToMany(mappedBy = "hospital",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    Set<Doctor> doctorSet;

    @OneToMany(mappedBy = "hospital",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    Set<PatientCard> patientCardSet;

    public Hospital(HospitalInfo hospitalInfo){
        this.Id=hospitalInfo.getId();
        this.code=hospitalInfo.getCode();
        this.name=hospitalInfo.getName();
        this.introduction=hospitalInfo.getIntroduction();
        this.image=hospitalInfo.getImage();
        this.url=hospitalInfo.getUrl();
        this.level=hospitalInfo.getLevel();
        this.location=hospitalInfo.getLocation();
        this.notice=hospitalInfo.getNotice();
        this.status=hospitalInfo.getStatus();
        this.password="123456";
    }

    public Hospital() {
    }
}
