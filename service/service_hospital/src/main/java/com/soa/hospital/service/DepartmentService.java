package com.soa.hospital.service;

import com.soa.hospital.model.Department;
import com.soa.hospital.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Department getById(String id) {
        Optional<Department> departById = departmentRepository.findById(id);
        return departById.orElse(null);
    }
}
