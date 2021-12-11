package com.soa.utils.error;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-12-11 10:11:07
 */
public class CommonError implements ServiceError {
    @Override
    public String getMessage() {
        return "error";
    }

    @Override
    public Integer getCode() {
        return 9;
    }
}
