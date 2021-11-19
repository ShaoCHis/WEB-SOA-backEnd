package com.soa.schedule.repository;

import com.soa.schedule.model.Doctor;
import org.springframework.data.repository.CrudRepository;

public interface DoctorRepository extends CrudRepository<Doctor,String> {

}
