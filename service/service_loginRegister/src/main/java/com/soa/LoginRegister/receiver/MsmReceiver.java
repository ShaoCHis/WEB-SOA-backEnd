package com.soa.LoginRegister.receiver;

import com.soa.LoginRegister.service.MsmService;
import com.soa.LoginRegister.views.MsmVo;
import com.soa.rabbit.constant.MqConst;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.amqp.core.Message;
import com.rabbitmq.client.Channel;
/**
 * @ program: demo
 * @ description:
 * @ author: ShenBo
 * @ date: 2021-12-10 10:33:24
 */
@Component
public class MsmReceiver {
    @Autowired
    MsmService msmService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConst.QUEUE_MSM, durable = "true"),
            exchange = @Exchange(value = MqConst.EXCHANGE_DIRECT_MSM),
            key = {MqConst.ROUTING_MSM}
    ))
    public void send(MsmVo msmVo, Message message, Channel channel) {
        msmService.sendRemind(msmVo.getMessage(),msmVo.getUserId());
    }

}
