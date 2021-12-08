package com.soa.order.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @ program: demo
 * @ description: 病人
 * @ author: ShenBo
 * @ date: 2021-11-18 17:41:55
 */
@Entity
@Getter
@Setter
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
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthday;
    @Column(name="certificate_type")
    private String certificateType;
    @Column(name="certificate_num")
    private String certificateNum;
    @Column(name="is_insure")
    private Integer isInsure;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_ID")
    private List<Card> cards;
}
