package com.soa.schedule.model;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

/**
 * @ program: demo
 * @ description: schedule
 * @ author: ShenBo
 * @ date: 2021-11-19 21:53:14
 */
@Entity
@Data
@Table(name="schedule")
@IdClass(SchedulePrimary.class)
@DynamicUpdate
public class Schedule {
    @Id
    @Column(name = "start_time")
    private Time startTime;
    @Id
    @Column(name = "end_time")
    private Time endTime;
    @Id
    private Date date;
    @Id
    @Column(name = "hospital_id")
    private String hospitalId;
    @Id
    @Column(name = "department_name")
    private String departmentName;
    @Id
    @Column(name = "doctor_name")
    private String doctorName;
    @Column(name = "reserved_number")
    private Integer reservedNumber;
    @Column(name = "available_number")
    private Integer availableNumber;
}
