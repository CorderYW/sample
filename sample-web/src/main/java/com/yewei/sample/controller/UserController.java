package com.yewei.sample.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.yewei.sample.api.UserApi;
import com.yewei.sample.common.utils.CopyBeanUtils;
import com.yewei.sample.common.utils.ListTransformUtil;
import com.yewei.sample.data.entity.UserModel;
import com.yewei.sample.data.mapper.UserMapper;
import com.yewei.sample.data.query.UserQueryParam;
import com.yewei.sample.request.UserQuery;
import com.yewei.sample.request.UserRequest;
import com.yewei.sample.respond.UserResponse;
import com.yewei.sample.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController implements UserApi {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    public UserResponse add(UserRequest userRequest){
        UserModel user = CopyBeanUtils.copy(userRequest,UserModel.class);
        int id = userService.add(user);
        UserResponse re = CopyBeanUtils.copy(user,UserResponse.class);
        return re;
    }

    /**
     * 功能描述：查找全部用户
     * 这里和下面是直接调用跳过Servise层，直接到DAO层
     */
    @GetMapping("findAll")
    public List<UserResponse> findAll(UserQuery query){
        UserQueryParam param = CopyBeanUtils.copy(query,UserQueryParam.class);
        List<UserModel> all = userMapper.listAllUsers(param);
        List<UserResponse> transform = new ArrayList<>();
        if(!CollectionUtils.isEmpty(all)){
            transform = ListTransformUtil.transform(all, UserResponse.class);
        }
        return transform;
    }

    @PostMapping("findUsersByPage")
    public Object findUsersByPage(UserQuery query){
        return userService.findUsersByPage(query);
    }

    /**
     * 查找单个用户
     */
    @GetMapping("find_by_id")
    public Object findById(long id){
        return userMapper.findById(id);
    }

    /**
     * 删除单个用户
     */
    @GetMapping("del_by_id")
    public Object delById(long id){
        userMapper.delete(id);
        return "";
    }

    /**
     *更新用户
     */
    @GetMapping("update")
    public Object update(String name,int id){
        UserModel user = new UserModel();
        user.setName(name);
        user.setId(id);
        userMapper.update(user);
        return "";
    }
}