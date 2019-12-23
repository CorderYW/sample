package com.yewei.sample.api;

import com.yewei.sample.common.db.PageResult;
import com.yewei.sample.request.IDRequest;
import com.yewei.sample.request.UserQueryRequest;
import com.yewei.sample.request.UserAddRequest;
import com.yewei.sample.request.UserUpdateRequest;
import com.yewei.sample.respond.UserResponse;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

//@FeignClient(name = "c2c", configuration = FeignConfig.class)
@RequestMapping(value = "/api/v1/user")
@Api(value = "user")
public interface UserApi {
    @PostMapping(value = "/add")
    UserResponse add(UserAddRequest userRequest) throws Exception;
    @GetMapping(value = "/listAllUsers")
    List<UserResponse> listAllUsers(UserQueryRequest query) throws Exception;

    @GetMapping("findUsersByPage")
    PageResult<UserResponse> findUsersByPage(UserQueryRequest query) throws Exception;

    @GetMapping(value = "/findById")
    UserResponse findById(IDRequest id) throws Exception;

    @PostMapping(value = "/deleteById")
    Boolean deleteById(IDRequest id) throws Exception;

    @PostMapping(value = "/updateById")
    Boolean updateById(UserUpdateRequest request) throws Exception;
}