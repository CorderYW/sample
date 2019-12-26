package com.yewei.sample.configuration;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 11  * redisson 配置类
 * 12  * Created on 2018/6/19
 * 13
 */
@Configuration
public class RedissonConfig {
    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;

    @Value("${spring.redis.password:}")
    private String password;

    @Bean
    public RedissonClient getRedisson() {

        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + host + ":" + port);
        //添加主从配置
        //        config.useMasterSlaveServers().setMasterAddress("").setPassword("").addSlaveAddress(new String[]{"",""});
        return Redisson.create(config);
    }

}
