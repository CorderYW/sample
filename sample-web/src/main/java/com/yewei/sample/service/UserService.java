package com.yewei.sample.service;

import com.yewei.sample.data.entity.UserModel;
import com.yewei.sample.request.UserQuery;

public interface UserService {

    //这里只写了个add方法，其它的直接在控制层调用Dao层，正常开发流程都应该写在Service层
    public int add(UserModel user);

    public Object findUsersByPage(UserQuery query);
}