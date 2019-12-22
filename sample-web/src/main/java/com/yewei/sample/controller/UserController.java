package com.yewei.sample.controller;

import java.util.ArrayList;
import java.util.List;

import com.yewei.sample.api.UserApi;
import com.yewei.sample.common.db.PageResult;
import com.yewei.sample.common.utils.CopyBeanUtils;
import com.yewei.sample.common.utils.ListTransformUtil;
import com.yewei.sample.data.entity.UserModel;
import com.yewei.sample.data.mapper.UserMapper;
import com.yewei.sample.data.query.UserQueryParam;
import com.yewei.sample.request.UserQueryRequest;
import com.yewei.sample.request.UserAddRequest;
import com.yewei.sample.request.UserUpdateRequest;
import com.yewei.sample.respond.UserResponse;
import com.yewei.sample.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class UserController implements UserApi {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    public UserResponse add(UserAddRequest userRequest)throws Exception{
        UserModel user = CopyBeanUtils.copy(userRequest,UserModel.class);
        int id = userService.add(user);
        UserResponse re = CopyBeanUtils.copy(user,UserResponse.class);
        return re;
    }

    public List<UserResponse> listAllUsers(UserQueryRequest query)throws Exception{
        return userService.listAllUsers(query);
    }

    public PageResult<UserResponse> findUsersByPage(UserQueryRequest query)throws Exception{
        UserQueryParam param = CopyBeanUtils.copy(query,UserQueryParam.class);
        return userService.findUsersByPage(param);
    }

    /**
     * 查找单个用户
     */
    public UserResponse findById(long id)throws Exception{
        return userService.findById(id);
    }

    /**
     * 删除单个用户
     */
    public Boolean deleteById(long id)throws Exception{
        return userService.deleteById(id);
    }

    /**
     *更新用户
     */
    public Boolean updateById(UserUpdateRequest request)throws Exception{
        return  userService.updateById(request);
    }
}