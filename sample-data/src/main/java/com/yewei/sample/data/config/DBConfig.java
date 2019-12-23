package com.yewei.sample.data.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.util.Strings;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.net.InetAddress;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.*;

/**
 * 数据连接基础配置类
 */
@Log4j2
public abstract class DBConfig {

    private String mapperLocation;

    @Resource
    protected ApplicationContext applicationContext;

    protected Integer maxConnectionsSizePerQuery;

    protected Boolean sqlShow;

    @Resource
    protected Environment env;

    static {
        System.setProperty("flyway.enabled", "false");
    }

    protected String getMapperLocation() {
        if (mapperLocation == null) {
            mapperLocation = StringUtils.removeEnd(this.getClass().getResource("").toString(), "configs/") + "**/*.xml";
        }
        return mapperLocation;
    }

    protected abstract DataSource dataSource() throws SQLException;


    /**
     * W允许直接调用这个进行实例化 DataSource
     * W直接走HikariDataSource
     * W建议走单表查询的可以使用这个进行实例化可以避免Sharding支持sql不全已经bug卡死问题
     * W建议链接使用aurora模式
     * @return
     */
    protected DataSource dataSource(String prefix) {
        HikariConfig config = new HikariConfig();
        config.setPoolName(env.getProperty(prefix + ".poolName", String.class));
        config.setDriverClassName(env.getProperty(prefix + ".driverClassName", String.class));
        config.setJdbcUrl(env.getProperty(prefix + ".jdbcUrl", String.class));
        config.setUsername(env.getProperty(prefix + ".username", String.class));
        config.setPassword(env.getProperty(prefix + ".password", String.class));
        config.setReadOnly(env.getProperty(prefix + ".readOnly", Boolean.class));
        config.setConnectionTimeout(env.getProperty(prefix + ".connectionTimeout", Long.class));
        config.setIdleTimeout(env.getProperty(prefix + ".idleTimeout", Long.class));
        config.setMaxLifetime(env.getProperty(prefix + ".maxLifetime", Long.class));
        config.setMaximumPoolSize(env.getProperty(prefix + ".maximumPoolSize", Integer.class));
        config.setMinimumIdle(env.getProperty(prefix + ".minimumIdle", Integer.class));
        return new HikariDataSource(config);
    }

    protected DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    protected SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(getMapperLocation()));
        sessionFactory.setConfiguration(this.getConfiguration());
        return sessionFactory.getObject();
    }

    protected Configuration getConfiguration() {
        Configuration config = new Configuration();
        // 使全局的映射器启用或禁用缓存
        config.setCacheEnabled(true);
        // 全局启用或禁用延迟加载。当禁用时，所有关联对象都会即时加载
        config.setLazyLoadingEnabled(false);
        // 配置默认的执行器。SIMPLE 就是普通的执行器；REUSE 执行器会重用预处理语句（prepared statements）； BATCH 执行器将重用语句并执行批量更新。
        config.setDefaultExecutorType(ExecutorType.SIMPLE);
        return config;
    }
    
}
