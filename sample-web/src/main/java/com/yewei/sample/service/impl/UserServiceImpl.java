package com.yewei.sample.service.impl;
import com.yewei.sample.common.db.PageResult;
import com.yewei.sample.common.error.BusinessException;
import com.yewei.sample.common.error.GeneralCode;
import com.yewei.sample.common.utils.CopyBeanUtils;
import com.yewei.sample.common.utils.ListTransformUtil;
import com.yewei.sample.data.entity.UserModel;
import com.yewei.sample.data.mapper.UserMapper;
import com.yewei.sample.data.query.UserQueryParam;
import com.yewei.sample.request.UserQueryRequest;
import com.yewei.sample.request.UserUpdateRequest;
import com.yewei.sample.respond.UserResponse;
import com.yewei.sample.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class UserServiceImpl implements UserService {

    //因为主类的@MapperScan方法，所以自动为UserMapper装配了一个userMapper对象
    @Autowired
    private UserMapper userMapper;

    //这里你传过去的时候user的id为null,而insert之后传回回来的user会把数据库中的id值带回来，真强大
    @Override
    public Long add(UserModel user) {
        log.info("添加用户:{}",user.toString());
        int insert = userMapper.insert(user);
        Long id = user.getId();
        return id;
    }

    @Override
    public List<UserResponse> listAllUsers(UserQueryRequest query) {
        UserQueryParam param = CopyBeanUtils.copy(query,UserQueryParam.class);
        List<UserModel> all = userMapper.listAllUsers(param);
        List<UserResponse> transform = new ArrayList<>();
        if(!CollectionUtils.isEmpty(all)){
            transform = ListTransformUtil.transform(all, UserResponse.class);
        }
        return transform;
    }

    @Override
    public PageResult<UserResponse> findUsersByPage(UserQueryParam query) {
        PageResult<UserResponse> result = new PageResult<>();
        UserQueryParam param = CopyBeanUtils.copy(query,UserQueryParam.class);
        List<UserModel> all = userMapper.getUsersByPage(param);
        Long aLong = userMapper.countUsers(param);
        if(!CollectionUtils.isEmpty(all)){
            List<UserResponse> transform = ListTransformUtil.transform(all, UserResponse.class);
            result.setRecords(transform);
            result.setTotal(aLong);
        }
        return result;
    }

    @Override
    public UserResponse findById(Long id) throws Exception {
        UserModel byId = userMapper.findById(id);
        if(null == byId){
            throw new BusinessException(GeneralCode.USER_NOT_EXIST);
        }
        return CopyBeanUtils.copy(byId,UserResponse.class);
    }

    @Override
    public Boolean deleteById(Long id) {
        return userMapper.deleteById(id);
    }

    @Override
    public Boolean updateById(UserUpdateRequest request){
        UserModel copy = CopyBeanUtils.copy(request, UserModel.class);
        return userMapper.updateById(copy);
    }
}
