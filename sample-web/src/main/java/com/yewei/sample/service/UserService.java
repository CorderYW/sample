package com.yewei.sample.service;

import com.yewei.common.db.req.PageResult;
import com.yewei.sample.data.entity.UserModel;
import com.yewei.sample.data.query.UserQueryParam;
import com.yewei.sample.request.UserQueryRequest;
import com.yewei.sample.request.UserUpdateRequest;
import com.yewei.sample.respond.UserResponse;

import java.util.List;

public interface UserService {

    //这里只写了个add方法，其它的直接在控制层调用Dao层，正常开发流程都应该写在Service层
    Long add(UserModel user)throws Exception;
    List<UserResponse> listAllUsers(UserQueryRequest query);
    PageResult<UserResponse> findUsersByPage(UserQueryParam query);
    UserResponse findById(Long id) throws Exception;
    Boolean deleteById(Long id)throws Exception;
    Boolean updateById(UserUpdateRequest request)throws Exception;
}
