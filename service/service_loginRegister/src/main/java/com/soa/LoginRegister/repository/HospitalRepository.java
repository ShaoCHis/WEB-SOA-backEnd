package com.soa.LoginRegister.repository;

import com.soa.LoginRegister.model.Hospital;
import org.springframework.data.repository.CrudRepository;

public interface HospitalRepository extends CrudRepository<Hospital,String> {
}
