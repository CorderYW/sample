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
import java.util.Date;
import java.util.List;


@Slf4j
@Service
public class StudentInfoServiceImpl implements StudentInfoService {

    //因为主类的@MapperScan方法，所以自动为UserMapper装配了一个userMapper对象
    @Autowired
    private StudentInfoMapper studentInfoMapper;

    @Autowired
    private CacheHelper cacheHelper;

    private static List<String> stuNames = new ArrayList<>();

    static{
        stuNames.add("唐佳豪");
        stuNames.add("文思颖");
        stuNames.add("汪泽洋");
        stuNames.add("刘玮祺");
        stuNames.add("邓煜杰");
        stuNames.add("邓然丹");
        stuNames.add("章艺馨");
        stuNames.add("侯雅菁");
        stuNames.add("严崇宇");
        stuNames.add("彭皓轩");
        stuNames.add("赵嘉露");
        stuNames.add("朱俊熙");
        stuNames.add("刘洪艳");
        stuNames.add("杨愉涛");
        stuNames.add("林贤圳");
        stuNames.add("陈鹏");
        stuNames.add("黄钎睿");
        stuNames.add("易兰鑫");
        stuNames.add("唐华");
        stuNames.add("李成");
        stuNames.add("叶静淇");
        stuNames.add("罗淑颖");
        stuNames.add("周楚云");
        stuNames.add("沈子依");
        stuNames.add("姜佳彤");
        stuNames.add("黎秉怡");
        stuNames.add("肖若雨");
        stuNames.add("占安安");
        stuNames.add("肖得帅");
        stuNames.add("唐汝谦");
        stuNames.add("罗宇军");
        stuNames.add("郭若妍");
        stuNames.add("周心怡");
        stuNames.add("郭泽宇");
        stuNames.add("胡希晨");
        stuNames.add("旷宇杰");
        stuNames.add("周珺林");
        stuNames.add("叶祖良");
        stuNames.add("周嘉迪");
        stuNames.add("刘友良");
        stuNames.add("刘子龙");
        stuNames.add("彭佳依");
        stuNames.add("周欣怡");
        stuNames.add("刘硕");
        stuNames.add("曹雅滢");
        stuNames.add("孙耀廷");
        stuNames.add("丁宁翔");
        stuNames.add("徐雅菲");
        stuNames.add("欧阳亮");
        stuNames.add("欧阳锦");
        stuNames.add("戴思丞");
        stuNames.add("刘世博");
        stuNames.add("欧家欣");
        stuNames.add("杨阳佳");
    }


    //这里你传过去的时候user的id为null,而insert之后传回回来的user会把数据库中的id值带回来，真强大
    @Override
    public Long add(StudentInfoModel user) {
        log.info("添加信息:{}",user.toString());
        String requestIp = IPUtils.getIpAddr();
        log.info("ip:{}",requestIp);
        if(!StringUtils.isEmpty(cacheHelper.getObject(user.getPhone(),String.class))){
            throw new BusinessException(SampleException.TRY_TOMORROW);
        }
        user.setStuName(user.getStuName().trim());
        if(!stuNames.contains(user.getStuName())){
            throw new BusinessException(SampleException.NAME_ERROR);
        }
        int insert = studentInfoMapper.insert(user);
        cacheHelper.setObjectExpire(user.getPhone(),user.getPhone(), DateHelper.getCurrentDateSeconds());
        Long id = user.getId();
        return id;
    }

    @Override
    public List<StudentInfoResponse> listAllUsers(StudentInfoQueryRequest query) {
        StudentInfoQueryParam param = CopyBeanUtils.copy(query, StudentInfoQueryParam.class);
        if(null == param.getStartDate()){
            Date today0 = DateHelper.getDawnDateUtcToBJ(-1);
            log.info("开始时间：{}",today0);
            param.setStartDate(today0);
        }
        if(null == param.getEndDate()){
            Date today24 = DateHelper.getDawnDateUtcToBJ(0);
            log.info("结束时间：{}",today24);
            param.setEndDate(today24);
        }
        List<StudentInfoModel> all = studentInfoMapper.listAllStudentInfo(param);
        List<StudentInfoResponse> transform = new ArrayList<>();

        StudentInfoModel stuM = new StudentInfoModel();
        for(String stu :stuNames) {
            stuM.setStuName(stu);
            if (!all.contains(stuM)) {
                StudentInfoModel notFilled = new StudentInfoModel();
                notFilled.setStuName(stu);
                notFilled.setFilled(Boolean.FALSE);
                all.add(notFilled);
            }
        }
        if(!CollectionUtils.isEmpty(all)){
            transform = ListTransformUtil.transform(all, StudentInfoResponse.class);
        }

        return transform;
    }

}
