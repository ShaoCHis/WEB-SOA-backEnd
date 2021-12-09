package com.soa.order.model;/**
 * Created By ShaoCHi
 * Date 2021/11/19 10:16 上午
 * Tongji University
 */

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

/**
 * author ShaoCHi
 * Date 2021/11/19 10:16 上午
 * Tongji University
 */

@Entity
@Data
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
  private Integer reservedNumber;//总预约数

  @Column(name = "available_number")
  private Integer availableNumber;//剩余可预约数

  @JsonBackReference
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "doctor_Id")
  private Doctor doctor;
}
