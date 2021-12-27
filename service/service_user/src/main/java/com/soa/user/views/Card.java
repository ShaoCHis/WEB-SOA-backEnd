package com.soa.user.views;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-12-27 19:49:53
 */
@Data
@AllArgsConstructor
public class Card {
    private String ID;
    private String patientId;
    private Integer type;
}
