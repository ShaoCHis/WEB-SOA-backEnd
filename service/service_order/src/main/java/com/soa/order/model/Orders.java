package com.soa.order.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-12-08 20:33:46
 */
@Entity
@Data
@Table(name="orders")
public class Orders {
    @Id
    @Column(name = "ID")
    private String ID;
    @Column(name = "reserve_ID")
    private String reserveID;
    private Integer cost;
    private Integer state;
    private Integer type;
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss")
    private Date time;
    @Column(name = "transaction_ID")
    private String transactionID;
}
