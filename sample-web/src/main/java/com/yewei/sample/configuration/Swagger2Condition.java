package com.yewei.sample.configuration;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class Swagger2Condition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        final String active = context.getEnvironment().getProperty("spring.profiles.active", String.class);
        return !StringUtils.endsWith(active, "prod");// 生产环境不加载
    }

}
