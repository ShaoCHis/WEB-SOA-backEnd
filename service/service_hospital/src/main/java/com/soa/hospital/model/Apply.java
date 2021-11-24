package com.soa.hospital.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @ program: demo
 * @ description: apply
 * @ author: ShenBo
 * @ date: 2021-11-18 15:32:14
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@Table(name="apply")
public class Apply implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String code;
    private String name;
    private String location;
    private String description;
    private String contact;
    private String image;
    private Integer status;
    private Integer level;
    @CreatedDate
    @Column(nullable = false, updatable = false)
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss")
    private Date time;
}
