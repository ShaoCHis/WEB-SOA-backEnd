package com.soa.hospital.repository;

import com.soa.hospital.model.Doctor;
import org.springframework.data.repository.CrudRepository;

public interface DoctorRepository extends CrudRepository<Doctor,String> {

}
