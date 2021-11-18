package com.soa.hospital.repository;

import com.soa.hospital.model.Department;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentRepository extends CrudRepository<Department,String> {

}
