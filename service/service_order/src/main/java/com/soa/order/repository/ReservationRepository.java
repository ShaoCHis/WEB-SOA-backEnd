package com.soa.order.repository;

import com.soa.order.model.Reservation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReservationRepository extends CrudRepository<Reservation,String> {

    @Query(value = "select * from reservation where schedule_ID=?1 and patient_ID=?2",nativeQuery = true)
    Reservation findReserved(Integer scheduleId, String patientId);

    @Query(value="select * from reservation where user_ID=?1",nativeQuery = true)
    List<Reservation> findUserRes(String userId);

    @Query(value="select * from reservation where hospital_ID=?1",nativeQuery = true)
    List<Reservation> findHospRes(String hospitalId);

    @Query(value="select * from reservation where schedule_ID=?1",nativeQuery = true)
    List<Reservation> findScheRes(Integer scheduleId);
}
