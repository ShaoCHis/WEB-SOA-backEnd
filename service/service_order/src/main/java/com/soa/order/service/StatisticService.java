package com.soa.order.service;

import com.soa.order.model.Reservation;
import com.soa.order.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-12-28 11:12:16
 */
@Service
public class StatisticService {

    @Autowired
    ReservationRepository reservationRepository;

    public int getMoneyTest(String fromDate,String endDate){
        List<Reservation> systemTimeReservation = reservationRepository.findSystemTimeReservation(fromDate, endDate);
        int answer=0;
        for(Reservation tmp:systemTimeReservation)
        {
            answer+=tmp.getCost();
        }
        return answer;
    }
}
