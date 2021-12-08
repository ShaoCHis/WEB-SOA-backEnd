package com.soa.hospital.views;

import lombok.Data;

import java.sql.Date;
import java.sql.Time;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-12-08 14:49:25
 */
@Data
public class ScheduleToUpdate {
    private Integer Id;
    private Integer availableNumber;
    private Integer reservedNumber;
    private Time startTime;
    private Time endTime;
    private Date date;
    private String doctorId;
}
