package com.soa.order.model;
/**
 * Created By ShaoCHi
 * Date 2021/11/18 7:31 下午
 * Tongji University
 */

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.soa.order.views.DoctorInfo;
import lombok.Data;
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
@Data
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

  public Doctor(DoctorInfo doctorInfo) {
    this.Id = doctorInfo.getId();
    this.name = doctorInfo.getName();
    this.introduction = doctorInfo.getIntroduction();
    this.age = doctorInfo.getAge();
    this.title = doctorInfo.getTitle();
    this.cost = doctorInfo.getCost();
  }

  public Doctor() {
  }
}
