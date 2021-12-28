package com.soa.order.views;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-12-28 14:42:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticResult {
    private String date;
    private Integer money;
}
