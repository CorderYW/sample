package com.yewei.sample.api;

import com.yewei.sample.request.UserQuery;
import com.yewei.sample.request.UserRequest;
import com.yewei.sample.respond.UserResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

//@FeignClient(name = "c2c", configuration = FeignConfig.class)
@RequestMapping(value = "/api/v1/user")
@Api(value = "user")
public interface UserApi {
    @PostMapping(value = "/add")
    UserResponse add(UserRequest userRequest);

     List<UserResponse> findAll(UserQuery query);
     Object findUsersByPage(UserQuery query);
     Object findById(long id);
     Object delById(long id);
     Object update(String name,int id);
}