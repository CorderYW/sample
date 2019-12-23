package com.yewei.sample.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.yewei.sample.api.UserApi;
import com.yewei.sample.common.db.PageResult;
import com.yewei.sample.common.utils.CopyBeanUtils;
import com.yewei.sample.common.utils.ListTransformUtil;
import com.yewei.sample.data.entity.UserModel;
import com.yewei.sample.data.mapper.UserMapper;
import com.yewei.sample.data.query.UserQueryParam;
import com.yewei.sample.request.IDRequest;
import com.yewei.sample.request.UserQueryRequest;
import com.yewei.sample.request.UserAddRequest;
import com.yewei.sample.request.UserUpdateRequest;
import com.yewei.sample.respond.UserResponse;
import com.yewei.sample.service.UserService;
import com.yewei.sample.utils.RestTemplateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;


@Slf4j
@RestController
public class UserController implements UserApi {

    @Autowired
    private UserService userService;

    @Override
    public UserResponse add(UserAddRequest userRequest)throws Exception{
        UserModel user = CopyBeanUtils.copy(userRequest,UserModel.class);
        Long id = userService.add(user);
        UserResponse re = CopyBeanUtils.copy(user,UserResponse.class);
        return re;
    }
    @Override
    public List<UserResponse> listAllUsers(UserQueryRequest query)throws Exception{
        return userService.listAllUsers(query);
    }
    @Override
    public PageResult<UserResponse> findUsersByPage(UserQueryRequest query)throws Exception{
        UserQueryParam param = CopyBeanUtils.copy(query,UserQueryParam.class);
        return userService.findUsersByPage(param);
    }
    /**
     * 查找单个用户
     */
    @Override
    public UserResponse findById(IDRequest request)throws Exception{
        return userService.findById(request.getId());
    }

    /**
     * 删除单个用户
     */
    @Override
    public Boolean deleteById(IDRequest request)throws Exception{
        return userService.deleteById(request.getId());
    }

    /**
     *更新用户
     */
    @Override
    public Boolean updateById(UserUpdateRequest request) throws Exception{
        return  userService.updateById(request);
    }
}