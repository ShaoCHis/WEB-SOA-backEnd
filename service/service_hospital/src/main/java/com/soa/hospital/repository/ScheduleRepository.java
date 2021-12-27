package com.soa.hospital.repository;


import com.soa.hospital.model.Schedule;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ScheduleRepository extends CrudRepository<Schedule,Integer> {

    @Query(value="select * from schedule where doctor_Id= ?1 ",nativeQuery = true)
    List<Schedule> findScheByDoc(String doctorId);
}
