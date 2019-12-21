package com.yewei.sample.data.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yewei.sample.common.annotations.DefaultDB;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * Created by robin.wu on 2018/12/5.
 */
@Configuration
@MapperScan(basePackages = "com.yewei.sample.data", annotationClass = DefaultDB.class,
        sqlSessionFactoryRef = "sqlSessionFactory")
public class DefaultDBConfig extends DBConfig {

    @Primary
    @Bean(name = "dataSource")
    @Override
    public DataSource dataSource() throws SQLException {
//        JSONObject datas = JSON.parseObject(super.env.getProperty("hikaricp.data", String.class));
        return this.dataSource("hikaricp.data.write");
    }

    @Primary
    @Bean(name = DefaultDB.TRANSACTION)
    @Override
    public DataSourceTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return super.transactionManager(dataSource);
    }

}
