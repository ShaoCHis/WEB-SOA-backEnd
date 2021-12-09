package com.soa.order.views;

import lombok.Data;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-11-24 14:38:35
 */
@Data
public class HospitalBaseInfo {
    private String Id;

    private String password;

    private String code;

    private String name;

    private String introduction;

    private String image;

    private String url;

    private Integer level;

    private String location;

    private String notice;

    private Integer status;
}
