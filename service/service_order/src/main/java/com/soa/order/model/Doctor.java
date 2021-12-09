package com.soa.order.model;
/**
 * Created By ShaoCHi
 * Date 2021/11/18 7:31 下午
 * Tongji University
 */

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.soa.order.views.DoctorInfo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * author ShaoCHi
 * Date 2021/11/18 7:31 下午
 * Tongji University
 */

@Entity
@Getter
@Setter
public class Doctor {
  @Id
  @Column(name = "ID")
  private String Id;

  private String name;

  private String introduction;

  private Integer age;

  private String title;

  private Integer cost;

  @JsonBackReference
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "hospital")
  private Hospital hospital;

  @JsonBackReference
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "department")
  private Department department;

  @JsonBackReference
  @OneToMany(mappedBy = "doctor",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
  private Set<Schedule> scheduleSet;

  public Doctor() {
  }

  public Doctor(DoctorInfo doctorInfo) {
    this.Id = doctorInfo.getId();
    this.name = doctorInfo.getName();
    this.introduction = doctorInfo.getIntroduction();
    this.age = doctorInfo.getAge();
    this.title = doctorInfo.getTitle();
    this.cost = doctorInfo.getCost();
  }

  public Set<Schedule> getScheduleSet() {
    return scheduleSet;
  }

  public void setScheduleSet(Set<Schedule> scheduleSet) {
    this.scheduleSet = scheduleSet;
  }

  public String getId() {
    return Id;
  }

  public void setId(String id) {
    Id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getIntroduction() {
    return introduction;
  }

  public void setIntroduction(String introduction) {
    this.introduction = introduction;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Integer getCost() {
    return cost;
  }

  public void setCost(Integer cost) {
    this.cost = cost;
  }

  public Hospital getHospital() {
    return hospital;
  }

  public void setHospital(Hospital hospital) {
    this.hospital = hospital;
  }

  public Department getDepartment() {
    return department;
  }

  public void setDepartment(Department department) {
    this.department = department;
  }
}
