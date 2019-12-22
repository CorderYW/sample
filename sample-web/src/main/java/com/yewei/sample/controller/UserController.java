package com.yewei.sample.controller;

import java.util.Date;

import com.yewei.sample.data.entity.UserModel;
import com.yewei.sample.data.mapper.UserMapper;
import com.yewei.sample.request.UserQuery;
import com.yewei.sample.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/user/")
public class UserController {

    //在UserServiceImpl定义了@Service实现类所以可以得到默认首字母小写的对象
    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    /**
     * 功能描述: user 保存接口
     */
    @GetMapping("add")
    public Object add(){

        UserModel user = new UserModel();
        user.setAge(11);
        user.setCreateTime(new Date());
        user.setName("张三");
        user.setPhone("1880177");
        int id = userService.add(user);

        return user.getId();
    }

    /**
     * 功能描述：查找全部用户
     * 这里和下面是直接调用跳过Servise层，直接到DAO层
     */
    @GetMapping("findAll")
    public Object findAll(){

        return userMapper.getAll();
    }

    @GetMapping("findUsersByPage")
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