package com.soa.user.service;

import com.soa.user.model.Patient;
import com.soa.user.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-11-18 17:46:42
 */
@Service
public class PatientService {
    @Autowired
    PatientRepository patientRepository;

    public Patient getById(String id) {
        Optional<Patient> byId = patientRepository.findById(id);
        Patient patient = byId.orElse(null);
        return patient;
    }
}
