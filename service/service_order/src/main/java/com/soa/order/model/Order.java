package com.soa.order.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

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
    private Integer type;
    @CreatedDate
    @Column(nullable = false, updatable = false)
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss")
    private Date time;
}
