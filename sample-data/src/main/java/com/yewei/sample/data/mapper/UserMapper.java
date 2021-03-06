package com.yewei.sample.data.mapper;
import java.util.List;

import com.yewei.sample.common.annotations.DefaultDB;
import com.yewei.sample.data.entity.UserModel;
import com.yewei.sample.data.query.UserQueryParam;
import org.apache.ibatis.annotations.*;

/**
 * 功能描述：访问数据库的接口
 */
@DefaultDB
public interface UserMapper {

    //推荐使用#{}取值，不要用${},因为存在注入的风险
//    @Insert("INSERT INTO user(name,phone,create_time,age) VALUES(#{name}, #{phone}, #{createTime},#{age})")
//    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")   //keyProperty java对象的属性；keyColumn表示数据库的字段
    int insert(UserModel user);

    //column指数据库中的列，property是指实体的属性名，如果一致就不需要写
    List<UserModel> listAllUsers(UserQueryParam query);
    Long countUsers(UserQueryParam query);

    List<UserModel> getUsersByPage(UserQueryParam query);

    UserModel findById(Long id);

    boolean updateById(UserModel user);

    boolean deleteById(Long userId);


}
