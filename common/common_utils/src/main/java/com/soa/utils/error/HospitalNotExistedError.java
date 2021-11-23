package com.soa.utils.error;

public class HospitalNotExistedError implements ServiceError{

    @Override
    public String getMessage() {
        return "Hospital isn't existed!";
    }

    @Override
    public Integer getCode() {return 3;}
}
