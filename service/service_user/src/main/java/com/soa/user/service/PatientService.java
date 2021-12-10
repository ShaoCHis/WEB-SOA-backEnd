package com.soa.user.service;

import com.soa.user.model.Patient;
import com.soa.user.repository.PatientRepository;
import com.soa.user.views.PatientVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public boolean addPatient(String userId ,PatientVo patientVo) {
        Patient patient=new Patient();
        patient.setPatientId(patientVo.getPatientId());
        patient.setUserId(userId);
        patient.setBirthday(patientVo.getBirthday());
        patient.setIsInsure(patientVo.getIsInsure());
        patient.setName(patientVo.getName());
        patient.setSex(patientVo.getSex());
        patient.setPhoneNumber(patientVo.getPhoneNumber());
        patientRepository.save(patient);
        return true;
    }

    @Transactional
    public boolean deletePatient(String patientId) {
        boolean existsById = patientRepository.existsById(patientId);
        if(existsById)
        {
            patientRepository.deleteById(patientId);
            return true;
        }
        return false;
    }
}
