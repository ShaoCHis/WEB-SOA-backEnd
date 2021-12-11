package com.soa.order.service;

import com.soa.order.model.Orders;
import com.soa.order.model.Reservation;
import com.soa.order.repository.OrdersRepository;
import com.soa.rabbit.service.RabbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-12-08 19:31:47
 */
@Service
public class OrdersService {

    @Autowired
    RabbitService rabbitService;

    @Autowired
    OrdersRepository ordersRepository;

    @Transactional
    public void saveOrderInfo(Reservation reservation, int type) {
        //首先查询是否存在相同订单
        Iterable<Orders> all = ordersRepository.findAll();
        for(Orders tmp:all){
            if(tmp.getReserveID().equals(reservation.getID()));
                return ;//已存在order
        }
        //添加order
        Orders orders =new Orders();
        orders.setID(reservation.getID());
        orders.setReserveID(reservation.getID());
        orders.setCost(reservation.getCost());
        orders.setType(type);
        orders.setState(0);//待支付
        orders.setTime(new Date());
        ordersRepository.save(orders);
    }

    public void paySuccess(String out_trade_no, Map<String, String> resultMap) {

    }
}
