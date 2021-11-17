package com.soa.hospital.repository;

import com.soa.hospital.model.Hospital;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface HospInfoRepository extends CrudRepository<Hospital,String> {

//    @Modifying
//    @Query("")
//    int updateImage();



}
