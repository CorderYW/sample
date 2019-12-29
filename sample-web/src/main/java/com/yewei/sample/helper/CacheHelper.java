package com.yewei.sample.helper;

import com.google.common.collect.Maps;
import com.yewei.sample.data.entity.UserModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.ObjectUtils;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by yewei on 2019-08-26.
 */
@Slf4j
@Component
public class CacheHelper {

    @Autowired
    private RedissonClient redissonClient;

    public static final String USER_INFO = "userInfo:";
    public static final long userInfoExpireSeconds = 60L * 2;
//
//    public RAtomicLong getDailyExpireLong(String key) {
//        RAtomicLong atomicLong = redissonClient.getAtomicLong(key);
//        if (!atomicLong.isExists()) {
//            atomicLong.set(0l);
//            atomicLong.expire(DateHelper.getCurrentDateSeconds(), TimeUnit.SECONDS);
//        }
//        return atomicLong;
//    }
//
//    public long getUserDoSomethingCount(Long userId, String key) {
//        RAtomicLong num = getDailyExpireLong(userId+key);
//        return num.get();
//    }

    public <T>T getObject(String key,Class<T> t){
        RBucket<T> mobile = redissonClient.getBucket(key);
        return mobile.get();
    }

    public void setObjectExpire(String key,Object t,Long expireSeconds){
        RBucket<Object> userModelRBucket = redissonClient.getBucket(key);
        userModelRBucket.set(t);
        userModelRBucket.expire(expireSeconds, TimeUnit.SECONDS);
    }

    public void setObject(String key,Object t){
        setObjectExpire(key,t,null);
    }
}
