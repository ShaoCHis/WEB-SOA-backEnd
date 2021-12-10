package com.soa.hospital.receiver;

import com.rabbitmq.client.Channel;
import com.soa.hospital.model.Schedule;
import com.soa.hospital.views.ScheduleMqVo;
import com.soa.hospital.service.ScheduleService;
import com.soa.rabbit.constant.MqConst;
import com.soa.rabbit.service.RabbitService;
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
 * @ date: 2021-12-07 22:18:02
 */
@Component
public class HospitalReceiver {
    @Autowired
    private ScheduleService scheduleService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConst.QUEUE_ORDER, durable = "true"),
            exchange = @Exchange(value = MqConst.EXCHANGE_DIRECT_ORDER),
            key = {MqConst.ROUTING_ORDER}
    ))
    public void receiver(int scheduleId, Message message, Channel channel) throws IOException {
        //下单成功更新预约数,指定schedule可预约数-1
        Schedule schedule = scheduleService.getSchedule(scheduleId);
        schedule.setAvailableNumber(schedule.getAvailableNumber()-1);
        scheduleService.update(schedule);
    }
}