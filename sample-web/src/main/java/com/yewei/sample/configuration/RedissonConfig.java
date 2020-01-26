package com.yewei.sample.configuration;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;
import java.util.Scanner;

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

    public static void main(String[] args) {
        Random r = new Random();
        int i = r.nextInt(1000)+1;
        System.out.println("-------猜数字游戏---------" );
        System.out.println("猜猜我是几？（0-1000）" );
        System.out.print("请输入：");
        Scanner s = new Scanner(System.in);
        int input = s.nextInt();

        int count = 1;
        while(input!=i){
            if(input<i){
                System.out.print("你猜少了，重新猜：");
            }else{
                System.out.print("你猜多了，重新猜：");
            }
            count ++;

            input = s.nextInt();
        }

        System.out.println("恭喜你，你猜对了，一共用了"+count+"次");

    }

}
