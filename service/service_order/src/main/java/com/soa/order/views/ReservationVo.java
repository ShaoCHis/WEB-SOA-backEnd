package com.soa.order.views;

import lombok.Data;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-12-09 21:59:27
 */
@Data
public class ReservationVo {
    private String hospitalID;
    private String hospitalName;
    private String departmentID;
    private String departmentName;
    private String doctorName;
    private String doctorTitle;
    private Integer cost;
}
