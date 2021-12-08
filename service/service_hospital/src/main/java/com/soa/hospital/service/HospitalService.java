package com.soa.hospital.service;

import com.soa.hospital.model.*;
import com.soa.hospital.repository.*;
import com.soa.hospital.views.*;
import com.soa.utils.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * @ program: demo
 * @ description: HospitalService
 * @ author: ShenBo
 * @ date: 2021-11-16 18:33:35
 */
@Service
public class HospitalService {
    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    HospitalRepository hospInfoRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    PatientCardRepository patientCardRepository;

    public Hospital getById(String id) {
        Optional<Hospital> hospById = hospInfoRepository.findById(id);
        return hospById.orElse(null);
    }

    @Transactional
    public boolean update(HospitalBaseInfo hospitalInfo) {
        Hospital hospital=new Hospital(hospitalInfo);
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
    public void updateNoticeById(HospitalBaseInfo hospitalInfo) {
        Hospital hospital=new Hospital(hospitalInfo);
        Optional<Hospital> byId = hospInfoRepository.findById(hospital.getId());
        Hospital hospital1=byId.orElse(null);
        hospital1.setNotice(hospital.getNotice());
        Hospital hosp = hospInfoRepository.save(hospital1);
    }

    @Transactional
    public void updateLogoById(HospitalBaseInfo hospitalInfo) {
        Hospital hospital=new Hospital(hospitalInfo);
        Optional<Hospital> byId = hospInfoRepository.findById(hospital.getId());
        Hospital hospital1=byId.orElse(null);
        hospital1.setImage(hospital.getImage());
        Hospital hosp = hospInfoRepository.save(hospital1);
    }

    @Transactional
    public void updatePassById(HospitalBaseInfo hospitalInfo) {
        Hospital hospital=new Hospital(hospitalInfo);
        Optional<Hospital> byId = hospInfoRepository.findById(hospital.getId());
        Hospital hospital1=byId.orElse(null);
        hospital1.setPassword(hospital.getPassword());
        Hospital hosp = hospInfoRepository.save(hospital1);
    }

    //保存科室信息
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

    @Transactional
    public Result<String> joinSystem(HospitalInfo body) {
        Hospital newHospital=new Hospital(body);
        hospInfoRepository.save(newHospital);

        Hospital hospital = hospInfoRepository.findById(body.getId()).get();
        List<Department> departments = new LinkedList<>();
        Iterable<Department> departmentIterable=departmentRepository.findAll();

        for(DepartmentWithDoctors departmentWithDoctors: body.getDepartmentWithDoctors()){
            Department department=null;
            for(Department department1:departmentIterable){
                if(department1.getName()==departmentWithDoctors.getName()) {
                    department=department1;
                    break;
                }
            }
            if(department==null)
                department=new Department(departmentWithDoctors);
            departmentRepository.save(department);
            departments.add(department);
            for(DoctorInfo doctorInfo:departmentWithDoctors.getDoctorList()){
                Doctor doctor=new Doctor();
                doctor.setId(doctorInfo.getId());
                doctor.setDepartment(department);
                doctor.setHospital(hospital);
                doctor.setAge(doctorInfo.getAge());
                doctor.setCost(doctorInfo.getCost());
                doctor.setTitle(doctorInfo.getTitle());
                doctor.setIntroduction(doctorInfo.getIntroduction());
                doctor.setName(doctorInfo.getName());
                doctorRepository.save(doctor);
                for(ScheduleInfo scheduleInfo:doctorInfo.getScheduleInfoList()){
                    Schedule schedule=new Schedule();
                    schedule.setDate(scheduleInfo.getDate());
                    schedule.setStartTime(new Time(scheduleInfo.getStartTime().getTime()-28800000-5760000));
                    schedule.setEndTime(new Time(scheduleInfo.getEndTime().getTime()-28800000-5760000));
                    schedule.setReservedNumber(scheduleInfo.getReservedNumber());
                    schedule.setAvailableNumber(scheduleInfo.getAvailableNumber());
                    schedule.setDoctor(doctor);
                    scheduleRepository.save(schedule);
                }
            }
        }
        hospital.setDepartments(departments);
        updateDepart(hospital);
        for(PatientInfo patientInfo:body.getPatientInfoList()){
            PatientCard patientCard=new PatientCard();
            patientCard.setPatientId(patientInfo.getPatientId());
            patientCard.setCardId(patientInfo.getCardId());
            patientCard.setType(patientInfo.getType());
            patientCard.setHospital(hospital);
            patientCardRepository.save(patientCard);
        }
        return Result.wrapSuccessfulResult("join Success!!!");
    }

    @Transactional
    public Result<String> getInformation(String id, List<PatientInfo> body) {
        Hospital hospital= getById(id);
        for(PatientInfo patientInfo:body){
            PatientCard patientCard=new PatientCard();
            patientCard.setHospital(hospital);
            patientCard.setType(patientInfo.getType());
            patientCard.setCardId(patientInfo.getCardId());
            patientCard.setPatientId(patientInfo.getPatientId());
            patientCardRepository.save(patientCard);
        }
        return Result.wrapSuccessfulResult("get Information Success!");
    }
}