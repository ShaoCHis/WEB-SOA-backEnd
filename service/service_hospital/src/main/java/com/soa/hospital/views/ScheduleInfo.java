package com.soa.hospital.views;/**
 * Created By ShaoCHi
 * Date 2021/11/19 10:38 上午
 * Tongji University
 */

import lombok.Data;

import java.sql.Date;
import java.sql.Time;

/**
 * author ShaoCHi
 * Date 2021/11/19 10:38 上午
 * Tongji University
 */

@Data
public class ScheduleInfo {
  private Integer availableNumber;

  private Integer reservedNumber;

  private Time startTime;

  private Time endTime;

  private Date date;
}
