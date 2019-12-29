package com.yewei.sample.controller;

import com.alibaba.fastjson.JSONObject;
import com.yewei.sample.data.entity.UserModel;
import com.yewei.sample.rest.RubbishResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {
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
}
