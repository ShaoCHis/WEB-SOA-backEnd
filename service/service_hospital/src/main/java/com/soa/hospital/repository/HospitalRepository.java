package com.soa.hospital.repository;

import com.soa.hospital.model.Hospital;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HospitalRepository extends CrudRepository<Hospital,String> {

    @Query(value = "select * from hospital where name like %?1%" ,nativeQuery = true)
    List<Hospital> findByHospName(String content);
}
