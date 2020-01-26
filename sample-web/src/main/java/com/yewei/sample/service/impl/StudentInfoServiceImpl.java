package com.yewei.sample.service.impl;
import com.yewei.common.db.req.PageResult;
import com.yewei.common.error.BusinessException;
import com.yewei.common.helper.DateHelper;
import com.yewei.common.utils.CopyBeanUtils;
import com.yewei.common.utils.ListTransformUtil;
import com.yewei.sample.data.entity.StudentInfoModel;
import com.yewei.sample.data.entity.UserModel;
import com.yewei.sample.data.mapper.StudentInfoMapper;
import com.yewei.sample.data.mapper.UserMapper;
import com.yewei.sample.data.query.StudentInfoQueryParam;
import com.yewei.sample.data.query.UserQueryParam;
import com.yewei.sample.exception.SampleException;
import com.yewei.sample.helper.CacheHelper;
import com.yewei.sample.request.StudentInfoQueryRequest;
import com.yewei.sample.request.UserQueryRequest;
import com.yewei.sample.request.UserUpdateRequest;
import com.yewei.sample.respond.StudentInfoResponse;
import com.yewei.sample.respond.UserResponse;
import com.yewei.sample.service.StudentInfoService;
import com.yewei.sample.service.UserService;
import com.yewei.sample.utils.IPUtils;
import com.yewei.sample.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class StudentInfoServiceImpl implements StudentInfoService {

    //因为主类的@MapperScan方法，所以自动为UserMapper装配了一个userMapper对象
    @Autowired
    private StudentInfoMapper studentInfoMapper;

    @Autowired
    private CacheHelper cacheHelper;

    //这里你传过去的时候user的id为null,而insert之后传回回来的user会把数据库中的id值带回来，真强大
    @Override
    public Long add(StudentInfoModel user) {
        log.info("添加信息:{}",user.toString());
        String requestIp = IPUtils.getIpAddr();
        log.info("ip:{}",requestIp);
        if(!StringUtils.isEmpty(cacheHelper.getObject(user.getPhone(),String.class))){
            throw new BusinessException(SampleException.TRY_TOMORROW);
        }
        int insert = studentInfoMapper.insert(user);
        cacheHelper.setObjectExpire(user.getPhone(),user.getPhone(), DateHelper.getCurrentDateSeconds());
        Long id = user.getId();
        return id;
    }

    @Override
    public List<StudentInfoResponse> listAllUsers(StudentInfoQueryRequest query) {
        StudentInfoQueryParam param = CopyBeanUtils.copy(query, StudentInfoQueryParam.class);
        List<StudentInfoModel> all = studentInfoMapper.listAllStudentInfo(param);
        List<StudentInfoResponse> transform = new ArrayList<>();
        if(!CollectionUtils.isEmpty(all)){
            transform = ListTransformUtil.transform(all, StudentInfoResponse.class);
        }

        return transform;
    }

}
