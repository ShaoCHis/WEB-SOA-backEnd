package com.soa.order.views;/**
 * Created By ShaoCHi
 * Date 2021/11/18 7:53 下午
 * Tongji University
 */


import lombok.Data;

import java.util.List;

/**
 * author ShaoCHi
 * Date 2021/11/18 7:53 下午
 * Tongji University
 */

@Data
public class DepartmentWithDoctors {
  private String Id;

  private String name;

  private String introduction;

  private List<DoctorInfo> doctorList;
}
