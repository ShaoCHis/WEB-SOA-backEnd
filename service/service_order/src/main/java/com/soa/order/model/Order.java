package com.soa.order.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-12-08 20:33:46
 */
@Entity
@Data
public class Order {
    @Id
    @Column(name = "ID")
    private String ID;
    @Column(name = "reserve_ID")
    private String reserveID;
    private Integer cost;
    private Integer state;
}
