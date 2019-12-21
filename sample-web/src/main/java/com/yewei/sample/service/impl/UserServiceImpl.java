package com.yewei.sample.service.impl;
import com.yewei.sample.data.entity.UserModel;
import com.yewei.sample.data.mapper.UserMapper;
import com.yewei.sample.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    //因为主类的@MapperScan方法，所以自动为UserMapper装配了一个userMapper对象
    @Autowired
    private UserMapper userMapper;

    //这里你传过去的时候user的id为null,而insert之后传回回来的user会把数据库中的id值带回来，真强大
    @Override
    public int add(UserModel user) {
        userMapper.insert(user);
        int id = user.getId();
        return id;
    }
}
