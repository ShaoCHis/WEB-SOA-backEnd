package com.soa.schedule.model;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-11-19 22:16:08
 */
@Data
public class SchedulePrimary implements Serializable {
    private Time startTime;
    private Time endTime;
    private Date date;
    private String hospitalId;
    private String departmentName;
    private String doctorName;
}
