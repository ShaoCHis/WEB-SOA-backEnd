package com.soa.schedule.service;

import com.soa.schedule.model.Doctor;
import com.soa.schedule.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-11-19 22:38:16
 */
@Service
public class DoctorService {
    @Autowired
    DoctorRepository doctorRepository;

    public Doctor getById(String id) {
        Optional<Doctor> byId = doctorRepository.findById(id);
        Doctor doctor = byId.orElse(null);
        return doctor;
    }

    public List<Doctor> getHospDocList(String id){
        Iterable<Doctor> all = doctorRepository.findAll();
        List<Doctor> list=new ArrayList<>();
        for(Doctor tmp:all){
            if(tmp.getHospital().equals(id))
                list.add(tmp);
        }
        return list;
    }

    public List<Doctor> getDepartDocList(String hid,String did) {
        List<Doctor> hospDocList = getHospDocList(hid);
        List<Doctor> list=new ArrayList<>();
        for(Doctor tmp:hospDocList){
            if(tmp.getDepartment().equals(did))
                list.add(tmp);
        }
        return list;
    }

    @Transactional
    public void addDoctor(Doctor doctor) {
        doctorRepository.save(doctor);
    }
}
