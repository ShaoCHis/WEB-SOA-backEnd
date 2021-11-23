package com.soa.hospital.repository;

import com.soa.hospital.model.PatientCard;
import com.soa.hospital.views.PatientIds;
import org.springframework.data.repository.CrudRepository;

public interface PatientCardRepository extends CrudRepository<PatientCard, PatientIds> {

}
