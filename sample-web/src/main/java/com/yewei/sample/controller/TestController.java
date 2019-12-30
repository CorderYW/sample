package com.yewei.sample.controller;

import com.alibaba.fastjson.JSONObject;
import com.yewei.sample.constant.Constant;
import com.yewei.sample.data.entity.UserModel;
import com.yewei.sample.rest.RubbishResponse;
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

    @GetMapping("/makeOrderMQ")
    public void makeOrderMQ(Long delay) throws Exception{
        log.info("发送消息");
        UserModel u = new UserModel();
        u.setAge(11);
        u.setName("yewei");
        rabbitTemplate.convertAndSend(Constant.MQ_ORDER_NOTIFY_PAYED_QUEUE, u, message -> {
            MessageProperties messageProperties = message.getMessageProperties();
            // 设置这条消息的过期时间,delayMinute*60*1000 ms
            messageProperties.setExpiration(String.valueOf(delay * 1000));
            return message;
        });
    }
}
