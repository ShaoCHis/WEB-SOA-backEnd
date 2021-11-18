package com.soa.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @ program: demo
 * @ description: 病人
 * @ author: ShenBo
 * @ date: 2021-11-18 17:41:55
 */
@Entity
public class Patient {
    @Id
    @Column(name = "patient_ID")
    private String patientId;
    @Column(name = "user_ID")
    private String userId;
    private String name;
    @Column(name = "phone_Number")
    private String phoneNumber;
    private String sex;
    private Date birthday;
    @Column(name="certificate_type")
    private String certificateType;
    @Column(name="certificate_num")
    private String certificateNum;
    @Column(name="is_insure")
    private Integer isInsure;
}