package com.soa.user.repository;

import com.soa.user.model.Patient;
import org.springframework.data.repository.CrudRepository;

public interface PatientRepository extends CrudRepository<Patient,String> {

}
