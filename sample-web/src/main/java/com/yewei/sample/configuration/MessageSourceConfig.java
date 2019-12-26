package com.yewei.sample.configuration;

import com.yewei.sample.handler.BinanceReloadableResourceBundleMessageSource;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageSourceConfig {
    @Bean
    public MessageSource messageSource() {
        BinanceReloadableResourceBundleMessageSource messageSource = new BinanceReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath*:/i18n/messages");
        return messageSource;
    }
}
