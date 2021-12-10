package com.soa.order.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class User {
    @Id
    @Column(name = "user_ID")
    private String userId;
    private String name;
    private String password;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "open_ID")
    private String openId;
    private String email;
    private String salt;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_ID")
    private List<Patient> patients;
}
