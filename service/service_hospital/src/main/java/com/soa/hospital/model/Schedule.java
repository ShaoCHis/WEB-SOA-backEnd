package com.soa.hospital.model;/**
 * Created By ShaoCHi
 * Date 2021/11/19 10:16 上午
 * Tongji University
 */

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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "doctor_Id")
  private Doctor doctor;

  public Integer getId() {
    return Id;
  }

  public void setId(Integer id) {
    Id = id;
  }

  public Time getStartTime() {
    return startTime;
  }

  public void setStartTime(Time startTime) {
    this.startTime = startTime;
  }

  public Time getEndTime() {
    return endTime;
  }

  public void setEndTime(Time endTime) {
    this.endTime = endTime;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public Integer getReservedNumber() {
    return reservedNumber;
  }

  public void setReservedNumber(Integer reservedNumber) {
    this.reservedNumber = reservedNumber;
  }

  public Integer getAvailableNumber() {
    return availableNumber;
  }

  public void setAvailableNumber(Integer availableNumber) {
    this.availableNumber = availableNumber;
  }

  public Doctor getDoctor() {
    return doctor;
  }

  public void setDoctor(Doctor doctor) {
    this.doctor = doctor;
  }
}
