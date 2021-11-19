package com.soa.hospital.service;

import com.soa.hospital.model.Department;
import com.soa.hospital.model.Hospital;
import com.soa.hospital.repository.DepartmentRepository;
import com.soa.hospital.repository.HospInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @ program: demo
 * @ description: HospInfoService
 * @ author: ShenBo
 * @ date: 2021-11-16 18:33:35
 */
@Service
public class HospInfoService {
    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    HospInfoRepository hospInfoRepository;

    public Hospital getById(String id) {
        Optional<Hospital> hospById = hospInfoRepository.findById(id);
        return hospById.orElse(null);
    }

    @Transactional
    public boolean update(Hospital hospital) {
        Optional<Hospital> byId = hospInfoRepository.findById(hospital.getId());
        Hospital hospital1=byId.orElse(null);
        if(hospital1==null)
            return false;
        if(hospital.getPassword()!=null)
            hospital1.setPassword(hospital.getPassword());
        if(hospital.getCode()!=null)
            hospital1.setCode(hospital.getCode());
        if(hospital.getName()!=null)
            hospital1.setName(hospital.getName());
        if(hospital.getIntroduction()!=null)
            hospital1.setIntroduction(hospital.getIntroduction());
        if(hospital.getImage()!=null)
            hospital1.setImage(hospital.getImage());
        if(hospital.getUrl()!=null)
            hospital1.setUrl(hospital.getUrl());
        if(hospital.getLevel()!=null)
            hospital1.setLevel(hospital.getLevel());
        if(hospital.getLocation()!=null)
            hospital1.setLocation(hospital.getLocation());
        if(hospital.getNotice()!=null)
            hospital1.setNotice(hospital.getNotice());
        if(hospital.getStatus()!=null)
            hospital1.setStatus(hospital.getStatus());
        Hospital hosp = hospInfoRepository.save(hospital1);
        return true;
    }

    @Transactional
    public void updateNoticeById(Hospital hospital){
        Optional<Hospital> byId = hospInfoRepository.findById(hospital.getId());
        Hospital hospital1=byId.orElse(null);
        hospital1.setNotice(hospital.getNotice());
        Hospital hosp = hospInfoRepository.save(hospital1);
    }

    @Transactional
    public void updateLogoById(Hospital hospital) {
        Optional<Hospital> byId = hospInfoRepository.findById(hospital.getId());
        Hospital hospital1=byId.orElse(null);
        hospital1.setImage(hospital.getImage());
        Hospital hosp = hospInfoRepository.save(hospital1);
    }

    @Transactional
    public void updatePassById(Hospital hospital) {
        Optional<Hospital> byId = hospInfoRepository.findById(hospital.getId());
        Hospital hospital1=byId.orElse(null);
        hospital1.setPassword(hospital.getPassword());
        Hospital hosp = hospInfoRepository.save(hospital1);
    }

    @Transactional
    public void updateDepart(Hospital hospital) {
        Hospital hosp = hospInfoRepository.save(hospital);
    }

    public List<Hospital> getHospListByDepartId(String id) {
        Optional<Department> byId = departmentRepository.findById(id);
        Department department = byId.orElse(null);
        if(department==null)
            return null;
        List<Hospital> hospitals = department.getHospitals();
        return hospitals;
    }
}
