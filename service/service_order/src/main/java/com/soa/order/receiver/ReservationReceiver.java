package com.soa.order.receiver;

import com.rabbitmq.client.Channel;
import com.soa.order.model.Reservation;
import com.soa.order.model.Schedule;
import com.soa.order.repository.ReservationRepository;
import com.soa.order.views.ScheduleMqVo;
import com.soa.rabbit.constant.MqConst;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-12-30 10:28:27
 */
@Component
public class ReservationReceiver {

    @Autowired
    ReservationRepository reservationRepository;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConst.QUEUE_RESERVATION, durable = "true"),
            exchange = @Exchange(value = MqConst.EXCHANGE_DIRECT_RESERVATION),
            key = {MqConst.ROUTING_RESERVATION}
    ))
    public void receiver(Reservation reservation, Message message, Channel channel) throws IOException {
        //下单成功保存reservation信息
        reservationRepository.save(reservation);
    }
}
