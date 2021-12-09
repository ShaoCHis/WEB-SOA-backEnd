package com.soa.order.views;
/**
 * Created By ShaoCHi
 * Date 2021/11/18 7:52 下午
 * Tongji University
 */

import lombok.Data;

import java.util.List;

/**
 * author ShaoCHi
 * Date 2021/11/18 7:52 下午
 * Tongji University
 */


@Data
public class HospitalInfo {
  private String Id;

  private String password;

  private String code;

  private String name;

  private String introduction;

  private String image;

  private String url;

  private Integer level;

  private String location;

  private String notice;

  private List<DepartmentWithDoctors> departmentWithDoctors;

  private Integer status;

  private List<PatientInfo> patientInfoList;
}
