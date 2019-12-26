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

    public final String USER_INFO = "userInfo:";
    public final long userInfoExpireSeconds = 60L * 2;

    public RAtomicLong getDailyExpireLong(String key) {
        RAtomicLong atomicLong = redissonClient.getAtomicLong(key);
        if (!atomicLong.isExists()) {
            atomicLong.set(0l);
            atomicLong.expire(DateHelper.getCurrentDateSeconds(), TimeUnit.SECONDS);
        }
        return atomicLong;
    }

    public long getUserDoSomethingCount(Long userId, String key) {
        RAtomicLong num = getDailyExpireLong(userId+key);
        return num.get();
    }

    public void setUserInfo(Long userId, UserModel model) {
        RBucket<UserModel> userModelRBucket = redissonClient.getBucket(USER_INFO + userId);

        userModelRBucket.set(model);
        userModelRBucket.expire(userInfoExpireSeconds, TimeUnit.SECONDS);
    }

    public UserModel getUserInfo(Long userId) {
        RBucket<UserModel> mobile = redissonClient.getBucket(USER_INFO + userId);
        return mobile.get();
    }


}
