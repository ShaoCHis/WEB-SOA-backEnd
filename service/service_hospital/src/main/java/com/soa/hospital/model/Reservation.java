package com.soa.hospital.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;
import java.sql.Time;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-12-08 20:02:33
 */
@Entity
@Data
public class Reservation {
    @Id
    @Column(name = "ID")
    private String ID;
    @Column(name = "user_ID")
    private String userID;
    @Column(name = "patient_ID")
    private String patientID;
    private String patientName;
    private Integer cardType;
    private String cardNum;
    @Column(name = "hospital_ID")
    private String hospitalID;
    private String hospitalName;
    @Column(name = "department_ID")
    private String departmentID;
    private String departmentName;
    private String doctorName;
    private String doctorTitle;
    @Column(name = "schedule_ID")
    private String scheduleID;
    private Integer number;
    private Date reserveDate;
    private Time reserveTime;
    private Integer cost;
    private Integer state;
}
