package com.soa.hospital.service;

import com.soa.hospital.model.Department;
import com.soa.hospital.model.Doctor;
import com.soa.hospital.model.Hospital;
import com.soa.hospital.repository.DoctorRepository;
import com.soa.hospital.repository.HospitalRepository;
import com.soa.hospital.views.DoctorInfo;
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
 * @ date: 2021-11-24 10:19:30
 */
@Service
public class DoctorService {
    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    HospitalRepository hospitalRepository;

    public Doctor getById(String id) {
        Optional<Doctor> byId = doctorRepository.findById(id);
        Doctor doctor = byId.orElse(null);
        return doctor;
    }

    public List<Doctor> getHospDocList(String id){
        Iterable<Doctor> all = doctorRepository.findAll();
        List<Doctor> list=new ArrayList<>();
        for(Doctor tmp:all){
            if(tmp.getHospital().getId().equals(id))
                list.add(tmp);
        }
        return list;
    }

    public List<Doctor> getDepartDocList(String hid,String did) {
        List<Doctor> hospDocList = getHospDocList(hid);
        List<Doctor> list=new ArrayList<>();
        for(Doctor tmp:hospDocList){
            if(tmp.getDepartment().getId().equals(did))
                list.add(tmp);
        }
        return list;
    }

    @Transactional
    public boolean addDoctor(String hid,String did,DoctorInfo doctorInfo) {
        Doctor doctor=new Doctor(doctorInfo);
        //这里需要查询医院id和科室id信息是否合法
        Optional<Hospital> byId = hospitalRepository.findById(hid);
        Hospital hospital=byId.orElse(null);
        if(hospital==null)
            return false;
        List<Department> departments = hospital.getDepartments();
        for(Department tmp:departments)
        {
            if(tmp.getId().equals(did)){
                doctor.setHospital(hospital);
                doctor.setDepartment(tmp);
                doctorRepository.save(doctor);
                return true;
            }
        }
        return false;
    }

    @Transactional
    public boolean updateDoctor(DoctorInfo doctorInfo) {
        Doctor doctor = getById(doctorInfo.getId());
        if(doctorInfo.getAge()!=null)
            doctor.setAge(doctorInfo.getAge());
        if(doctorInfo.getCost()!=null)
            doctor.setCost(doctorInfo.getCost());
        if(doctorInfo.getIntroduction()!=null)
            doctor.setIntroduction(doctorInfo.getIntroduction());
        if(doctorInfo.getName()!=null)
            doctor.setName(doctorInfo.getName());
        if(doctorInfo.getTitle()!=null)
            doctor.setTitle(doctorInfo.getTitle());
        doctorRepository.save(doctor);
        return true;
    }
}