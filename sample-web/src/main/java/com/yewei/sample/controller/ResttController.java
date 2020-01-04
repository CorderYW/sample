package com.yewei.sample.controller;

import com.google.common.collect.Maps;
import com.yewei.sample.api.UserApi;
import com.yewei.sample.data.entity.UserModel;
import com.yewei.sample.data.mapper.UserMapper;
import com.yewei.sample.data.query.UserQueryParam;
import com.yewei.sample.request.UserAddRequest;
import com.yewei.sample.request.UserQueryRequest;
import com.yewei.sample.request.UserUpdateRequest;
import com.yewei.sample.respond.UserResponse;
import com.yewei.sample.rest.MobileResult;
import com.yewei.sample.rest.RubbishResponse;
import com.yewei.sample.service.UserService;
import com.yewei.sample.utils.RestTemplateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/rest")
public class ResttController {

    @GetMapping("/getRubbishResponse")
    public RubbishResponse getRubbishResponse() throws Exception{
        Map<String,String> param = Maps.newHashMap();
        param.put("key","4f900bac1ffc591db91d9ac8790dbca8");
        RestTemplateUtil ui = new RestTemplateUtil();
        RubbishResponse s = ui.postRequest("http://apis.juhe.cn/rubbish/category", param, RubbishResponse.class);
        return s;
    }

    @GetMapping("/getMobile")
    public MobileResult getMobile(String mobile) throws Exception{
        Map<String,Object> param = Maps.newHashMap();
        param.put("key","5fd5a63f1713910db3b6cafbcbba3fef");
        param.put("phone",mobile);
        RestTemplateUtil ui = new RestTemplateUtil();
        MobileResult s = ui.getRequest("http://apis.juhe.cn/mobile/get", param, MobileResult.class);
        return s;
    }
}