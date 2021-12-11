package com.soa.order.service;

import com.soa.order.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-12-11 12:48:11
 */
@Service
public class WeixinService {

    @Autowired
    ReservationService reservationService;

    @Autowired
    OrderService orderService;

    //生成微信支付二维码
    public Map createNative(String reservationId) {
        Reservation reservation = reservationService.getReservationById(reservationId);
        if(reservation==null)
            return null;
        orderService.saveOrderInfo(reservation, 1);//1代表type为微信支付



        return null;
    }

    public Map<String, String> queryPayStatus(String reservationId) {
        return null;
    }
}
