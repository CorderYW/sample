package com.yewei.sample.controller;

import com.alibaba.fastjson.JSONObject;
import com.yewei.sample.constant.Constant;
import com.yewei.sample.data.entity.UserModel;
import com.yewei.sample.rest.RubbishResponse;
import com.yewei.service.ChinaSmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    //https://www.toutiao.com/i6746483118800126478/
    @GetMapping("/jsonToModel")
    public UserModel jsonToModel() throws Exception{
        String jsonStr = "{\n" +
                " \"userId\" : 2003,\n" +
                " \"name\" : \"张三\",\n" +
                " \"age\" : 28\n" +
                "}";
        UserModel userModel = JSONObject.parseObject(jsonStr, UserModel.class);

        return userModel;
    }

    @Autowired(required = false)
    private ChinaSmsService chinaSmsService;

    @GetMapping("/makeOrderDelayMQ")
    public void makeOrderDelayMQ(Long delay) throws Exception{
        log.info("发送消息");
        UserModel u = new UserModel();
        u.setAge(11);
        u.setName("yewei");
        rabbitTemplate.convertAndSend(Constant.MQ_QUEUE_ORDER_NOTIFY_PAYED, u, message -> {
            MessageProperties messageProperties = message.getMessageProperties();
            // 设置这条消息的过期时间,delayMinute*60*1000 ms
            messageProperties.setExpiration(String.valueOf(delay * 1000));
            return message;
        });
    }
    @GetMapping("/makeOrderNormalMQ")
    public void makeOrderNormalMQ() throws Exception{
        log.info("发送消息：");
        UserModel u = new UserModel();
        u.setAge(22);
        u.setName("yewei22");
        rabbitTemplate.convertAndSend(Constant.ORDER_EXCHANGE,Constant.ROUTING_KEY_MQ_USER, u);
    }
    @GetMapping("/sendSms")
    public void sendSms(String mobileNumber,String content) throws Exception{
        chinaSmsService.sendSms(mobileNumber,"【币安TEST】"+content);
    }
}
