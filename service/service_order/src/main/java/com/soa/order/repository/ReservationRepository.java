package com.soa.order.repository;

import com.soa.order.model.Reservation;
import org.springframework.data.repository.CrudRepository;

public interface ReservationRepository extends CrudRepository<Reservation,String> {
}
