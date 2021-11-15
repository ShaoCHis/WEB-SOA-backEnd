package com.soa.utils.error;

public class UserNotExistedError implements ServiceError{

    @Override
    public String getMessage() {
        return "User isn't existed!";
    }

    @Override
    public Integer getCode() {return 10;}
}
