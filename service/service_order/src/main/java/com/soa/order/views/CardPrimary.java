package com.soa.order.views;

import lombok.Data;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * @ program: demo
 * @ description: 主键类
 * @ author: ShenBo
 * @ date: 2021-11-19 19:42:01
 */
@Data
public class CardPrimary implements Serializable {
    private String patientId;
    private Integer type;
    private String cardId;
}
