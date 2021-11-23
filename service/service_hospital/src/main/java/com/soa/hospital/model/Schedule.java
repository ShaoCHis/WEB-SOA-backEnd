package com.soa.hospital.model;/**
 * Created By ShaoCHi
 * Date 2021/11/19 10:16 上午
 * Tongji University
 */

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

/**
 * author ShaoCHi
 * Date 2021/11/19 10:16 上午
 * Tongji University
 */

@Entity
@Getter
@Setter
public class Schedule {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer Id;

  @Column(name = "start_time")
  private Time startTime;

  @Column(name = "end_time")
  private Time endTime;

  private Date date;

  @Column(name = "reserved_number")
  private Integer reservedNumber;

  @Column(name = "available_number")
  private Integer availableNumber;

  @JsonBackReference
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "doctor_Id")
  private Doctor doctor;
}
