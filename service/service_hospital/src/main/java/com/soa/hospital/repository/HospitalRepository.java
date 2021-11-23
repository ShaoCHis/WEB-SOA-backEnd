package com.soa.hospital.repository;

import com.soa.hospital.model.Hospital;
import org.springframework.data.repository.CrudRepository;

public interface HospitalRepository extends CrudRepository<Hospital,String> {

}
