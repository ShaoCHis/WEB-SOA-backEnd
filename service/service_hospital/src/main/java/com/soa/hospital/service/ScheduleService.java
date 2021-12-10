package com.soa.hospital.service;

import com.soa.hospital.model.Doctor;
import com.soa.hospital.model.Schedule;
import com.soa.hospital.repository.ScheduleRepository;
import com.soa.hospital.views.ScheduleInfo;
import com.soa.hospital.views.ScheduleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-11-24 10:19:49
 */
@Service
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    DoctorService doctorService;

    @Transactional
    public boolean addSchedule(String doctorId, ScheduleInfo scheduleInfo) {
        Doctor byId = doctorService.getById(doctorId);
        if(byId==null)
            //医生id不正确
            return false;
        Schedule schedule=new Schedule();
        schedule.setDate(scheduleInfo.getDate());
        schedule.setStartTime(new Time(scheduleInfo.getStartTime().getTime()-28800000-5760000));
        schedule.setEndTime(new Time(scheduleInfo.getEndTime().getTime()-28800000-5760000));
        schedule.setReservedNumber(scheduleInfo.getReservedNumber());
        schedule.setAvailableNumber(scheduleInfo.getAvailableNumber());
        schedule.setDoctor(byId);
        scheduleRepository.save(schedule);
        return true;
    }

    public List<Schedule> getScheduleList(String doctorId) {
        Iterable<Schedule> all = scheduleRepository.findAll();
        List<Schedule> scheduleList=new ArrayList<>();
        for(Schedule tmp:all){
            if(tmp.getDoctor().getId().equals(doctorId))
                scheduleList.add(tmp);
        }
        return scheduleList;
    }

    @Transactional
    public boolean deleteSchedule(Integer scheduleId) {

        Optional<Schedule> byId = scheduleRepository.findById(scheduleId);
        Schedule schedule = byId.orElse(null);
        if(schedule==null)
            return false;
        scheduleRepository.deleteById(scheduleId);
        return true;
    }

    public boolean updateSchedule(ScheduleVo scheduleVo) {
        //to write
        
        return false;
    }

    public List<Schedule> getHospSchedule(String hospitalId) {
        //to write
        return null;
    }

    public List<Schedule> getHospDepartSchedule(String hospitalId, String departmentId) {
        //to write
        return null;
    }

    public Schedule getSchedule(int scheduleId) {
        Optional<Schedule> byId = scheduleRepository.findById(scheduleId);
        Schedule schedule = byId.orElse(null);
        return schedule;
    }

    public void update(Schedule schedule) {
        //更新schedule
        //to write
    }
}