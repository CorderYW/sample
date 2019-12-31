package com.yewei.sample.configuration;

import com.yewei.sample.constant.Constant;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yewei on 2019-09-09.
 */
@Slf4j
@Component
public class MQConfiguration {
     private static Integer messageTTL=  60*60*1000;
    //交换机用于重新分配队列
    @Bean
    DirectExchange direct() {
        return new DirectExchange(Constant.ORDER_EXCHANGE);
    }
    //用于延时消费的队列
    @Bean
    public Queue notifyPayedDeadLetterQueue() {
        Queue queue = new Queue(Constant.MQ_QUEUE_ORDER_NOTIFY_PAYED_DEAD_LETTER,true,false,false);
        return queue;
    }


    //绑定交换机并指定routing key
    @Bean
    public Binding  notifyPayedDeadLetterBinding(DirectExchange exchange) {
        return BindingBuilder.bind(notifyPayedDeadLetterQueue()).to(exchange).with(Constant.ROUTING_KEY_MQ_ORDER_NOTIFY_PAYED);
    }

    //配置业务队列，默认里面的消息1小时失效。 发送的时候可以指定比这个小的值进行发送。消息过期时间会取小的。
    @Bean
    public Queue notifyPayedQueue() {
        Map<String,Object> args = new HashMap<>();
        args.put("x-message-ttl", messageTTL);
        args.put("x-dead-letter-exchange",Constant.ORDER_EXCHANGE);
        args.put("x-dead-letter-routing-key", Constant.ROUTING_KEY_MQ_ORDER_NOTIFY_PAYED);
        return new Queue(Constant.MQ_QUEUE_ORDER_NOTIFY_PAYED, true, false, false, args);
    }

    //USER QUEUE
    @Bean
    public Queue userQueue() {
        Queue queue = new Queue(Constant.MQ_QUEUE_USER,true,false,false);
        return queue;
    }
    @Bean
    public Binding  userQueueBinding(DirectExchange exchange) {
        return BindingBuilder.bind(userQueue()).to(exchange).with(Constant.ROUTING_KEY_MQ_USER);
    }
}
