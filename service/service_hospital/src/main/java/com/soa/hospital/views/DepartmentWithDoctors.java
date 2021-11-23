package com.soa.hospital.views;/**
 * Created By ShaoCHi
 * Date 2021/11/18 7:53 下午
 * Tongji University
 */


import com.soa.hospital.model.Department;
import lombok.Data;

import java.util.LinkedList;
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
