package com.soa.hospital.service;

import com.soa.hospital.model.Department;
import com.soa.hospital.model.Hospital;
import com.soa.hospital.repository.DepartmentRepository;
import com.soa.hospital.repository.HospInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-11-18 14:39:58
 */
@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    HospInfoRepository hospInfoRepository;

    public Department getById(String id) {
        Optional<Department> departById = departmentRepository.findById(id);
        return departById.orElse(null);
    }

    public List<Department> getListById(String id) {
        Optional<Hospital> byId = hospInfoRepository.findById(id);
        Hospital hospital = byId.orElse(null);
        if(hospital==null)
            return null;
        List<Department> departments = hospital.getDepartments();
        return departments;
    }
}
