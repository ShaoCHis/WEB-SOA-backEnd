package com.soa.order.service;

import com.soa.order.model.Order;
import com.soa.order.model.Reservation;
import com.soa.order.repository.OrderRepository;
import com.soa.rabbit.service.RabbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-12-08 19:31:47
 */
@Service
public class OrderService {

    @Autowired
    RabbitService rabbitService;

    @Autowired
    OrderRepository orderRepository;

    public void saveOrderInfo(Reservation reservation, int type) {
        //首先查询是否存在相同订单
        Iterable<Order> all = orderRepository.findAll();
        for(Order tmp:all){
            if(tmp.getReserveID().equals(reservation.getID()));
                return ;//已存在order
        }
        //添加order
        Order order=new Order();
        order.setID(reservation.getID());
        order.setReserveID(reservation.getID());
        order.setCost(reservation.getCost());
        order.setType(type);
        order.setState(0);//待支付
        orderRepository.save(order);
    }

    public void paySuccess(String out_trade_no, Map<String, String> resultMap) {
        
    }
}
