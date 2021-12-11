package com.soa.order.service;

import com.soa.order.model.Orders;
import com.soa.order.model.Reservation;
import com.soa.order.repository.OrdersRepository;
import com.soa.order.repository.ReservationRepository;
import com.soa.rabbit.service.RabbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

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

    @Autowired
    ReservationRepository reservationRepository;

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

    //成功时改变支付状态
    public void paySuccess(String orderId, Map<String, String> resultMap) {
        Optional<Orders> byId = ordersRepository.findById(orderId);
        Orders orders = byId.orElse(null);
        if(orders==null)
            return;
        //更新order信息
        orders.setState(1);//已支付
        orders.setTime(new Date());
        orders.setTransactionID(resultMap.get("transaction_id"));
        ordersRepository.save(orders);
        //更新reservation信息
        Optional<Reservation> reservationRepositoryById = reservationRepository.findById(orders.getReserveID());
        Reservation reservation = reservationRepositoryById.orElse(null);
        reservation.setState(1);//已支付
        reservationRepository.save(reservation);

        //TODO
        // rabbit发短信：
        // 您好，病人xxx成功预约xxx医院xxx科室的门诊，时间为xxx（日期时间），预约序号为第xxx位。


        //TODO
        // 医院财务
        // 调子系统api，医院端记录病人预约
        // 还有病人预约列表，医院端怎么查看

    }

    //根据ordersId获取order
    public Orders getOrdersById(String ordersId){
        Optional<Orders> byId = ordersRepository.findById(ordersId);
        Orders orders = byId.orElse(null);
        return orders;
    }

    //退款时修改reservation和orders的state
    @Transactional
    public void refundOrders(String ordersId,int type){
        //更新order信息
        Orders ordersById = getOrdersById(ordersId);
        if(ordersById==null)
            return;
        ordersById.setTime(new Date());
        ordersById.setState(type);//2为退款中,3为退款成功
        ordersRepository.save(ordersById);
        //更新reservation信息
        Optional<Reservation> reservationRepositoryById = reservationRepository.findById(ordersById.getReserveID());
        Reservation reservation = reservationRepositoryById.orElse(null);
        if(reservation==null)
            return;
        reservation.setState(type);//2为退款中,3为退款成功
        reservationRepository.save(reservation);
    }
}
