package com.yewei.sample.service;

import com.yewei.common.db.req.PageResult;
import com.yewei.sample.data.entity.StudentInfoModel;
import com.yewei.sample.data.entity.UserModel;
import com.yewei.sample.data.query.UserQueryParam;
import com.yewei.sample.request.StudentInfoQueryRequest;
import com.yewei.sample.request.UserQueryRequest;
import com.yewei.sample.request.UserUpdateRequest;
import com.yewei.sample.respond.StudentInfoResponse;
import com.yewei.sample.respond.UserResponse;

import java.util.List;

public interface StudentInfoService {
    Long add(StudentInfoModel user)throws Exception;
    List<StudentInfoResponse> listAllUsers(StudentInfoQueryRequest query);
}
