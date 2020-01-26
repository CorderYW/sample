package com.yewei.sample.controller;

import com.yewei.common.db.req.PageResult;
import com.yewei.common.utils.CopyBeanUtils;
import com.yewei.sample.api.UserApi;
import com.yewei.sample.common.advice.SystemFix;
import com.yewei.sample.data.entity.StudentInfoModel;
import com.yewei.sample.data.entity.UserModel;
import com.yewei.sample.data.query.UserQueryParam;
import com.yewei.sample.request.*;
import com.yewei.sample.respond.StudentInfoResponse;
import com.yewei.sample.respond.UserResponse;
import com.yewei.sample.service.StudentInfoService;
import com.yewei.sample.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/student/info")
public class StudentInfoController {

    @Autowired
    private StudentInfoService studentInfoService;

    @PostMapping("/addInfo")
    public StudentInfoResponse add(@RequestBody StudentInfoAddRequest userRequest) throws Exception {
        StudentInfoModel user = CopyBeanUtils.copy(userRequest, StudentInfoModel.class);
        Long id = studentInfoService.add(user);
        StudentInfoResponse re = CopyBeanUtils.copy(user, StudentInfoResponse.class);
        return re;
    }

    @PostMapping("/listAll")
    public List<StudentInfoResponse> listAllUsers(@RequestBody StudentInfoQueryRequest query) throws Exception {
        return studentInfoService.listAllUsers(query);
    }
}
