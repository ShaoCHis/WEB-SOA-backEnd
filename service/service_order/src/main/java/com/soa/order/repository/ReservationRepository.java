package com.soa.order.repository;

import com.soa.order.model.Reservation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReservationRepository extends CrudRepository<Reservation,String> {

    @Query(value = "select * from reservation tmp " ,nativeQuery = true)
    List<Reservation> getReservationList();

}
