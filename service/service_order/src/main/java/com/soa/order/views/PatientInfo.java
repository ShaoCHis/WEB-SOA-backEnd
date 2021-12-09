package com.soa.order.views;
/**
 * Created By ShaoCHi
 * Date 2021/11/20 4:51 下午
 * Tongji University
 */

import lombok.Data;

import java.sql.Date;

/**
 * author ShaoCHi
 * Date 2021/11/20 4:51 下午
 * Tongji University
 */

@Data
public class PatientInfo {
  private String patientId;

  private String cardId;

  private String name;

  private String phoneNumber;

  private String sex;

  private Date birthday;

  private Integer type;

  private Integer balance;
}
