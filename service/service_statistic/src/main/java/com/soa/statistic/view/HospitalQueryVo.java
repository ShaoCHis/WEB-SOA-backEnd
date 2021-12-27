package com.soa.statistic.view;

import lombok.Data;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-12-27 22:31:40
 */
@Data
public class HospitalQueryVo {
    private String hospitalId;
    private String reserveDateBegin;
    private String reserveDateEnd;
}
