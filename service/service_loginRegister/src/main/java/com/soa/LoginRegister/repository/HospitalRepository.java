package com.soa.LoginRegister.repository;

import com.soa.LoginRegister.model.Hospital;
import org.springframework.data.repository.CrudRepository;

import java.util.Objects;

public interface HospitalRepository extends CrudRepository<Hospital,String> {
    default Hospital findByCode(String code){
        Iterable<Hospital> hospitals=findAll();
        for(Hospital hospital:hospitals){
            if(Objects.equals(hospital.getCode(), code)){
                return hospital;
            }
        }
        return null;
    }

}
