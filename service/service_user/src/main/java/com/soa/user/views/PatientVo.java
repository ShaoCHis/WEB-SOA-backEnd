package com.soa.user.views;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-12-10 20:24:00
 */
@Data
public class PatientVo {
    private String patientId;
    private String name;
    private String phoneNumber;
    private String sex;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthday;
    private Integer isInsure;
}
