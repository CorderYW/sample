package com.yewei.sample.mq;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.yewei.sample.common.utils.TrackingUtils;
import com.yewei.sample.constant.Constant;
import com.yewei.sample.data.entity.UserModel;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by yewei on 2019-09-09.
 */
@Log4j2
@Component
public class UserListener {
    @RabbitListener(queues = Constant.MQ_QUEUE_USER)
    public void onNotifyPayedMessage(Message message, Channel channel) {
        log.info("收到user消息");
        Date date = new Date();

        String body = null;
        final long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            body = new String(message.getBody(), "utf-8");
            log.info("收到user消息,{},接收消息时间：{}", body, date);
            UserModel user = JSON.parseObject(body, UserModel.class);
            log.info("收到user消息,{},接收消息时间：{}", user, date);
        } catch (Exception e) {
            log.error("Order notify payed => 处理异常. body:{}", body, e);
        } finally {
            try {
                channel.basicAck(deliveryTag, false);
            } catch (Exception e) {
                log.error("Order notify payed => 消息ACK失败. ", e);
            }
            TrackingUtils.removeTracking();
        }
    }

}
