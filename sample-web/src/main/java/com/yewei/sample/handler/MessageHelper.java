package com.yewei.sample.handler;

import com.yewei.sample.common.error.ErrorCode;
import com.yewei.sample.common.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.Locale;

@Component
@Slf4j
public class MessageHelper {
    public static final String LANG = "lang";
    @Resource
    private MessageSource messageSource;

    public String getMessage(ErrorCode errorCode, Object... params) {
        String key = errorCode.getClass().getName() + "." + errorCode;
        return getMessage(key, params);
    }

    public String getMessage(ErrorCode errorCode, Locale locale, Object... params) {
        String key = errorCode.getClass().getName() + "." + errorCode;
        return getMessage(key, locale, params);
    }

    public String getMessage(String key, Object... params) {
        return getMessage(key, null, params);
    }

    public String getMessage(String key,Locale locale, Object... params) {
        // 国际化暂时只支持中英文，默认为英文
        if(locale ==null){
            locale = Locale.US;
            if (StringUtils.equals("cn", getLanguage())) {
                locale = Locale.CHINA;
            }
        }
        String msg;
        try {
            msg = messageSource.getMessage(new DefaultMessageSourceResolvable(key), locale);
            if (params != null) {
                msg = MessageFormat.format(msg, params);
            }
        } catch (NoSuchMessageException e) {
            msg = key;
        } catch (Throwable e) {
            log.error("国际化异常：", e);
            msg = key;
        }
        return msg;
    }

    private String getLanguage() {
        HttpServletRequest request = WebUtils.getHttpServletRequest();
        if (request != null) {
            String language = request.getHeader(LANG);
            if (StringUtils.isBlank(language)) {
                language = request.getParameter(LANG);
            }
            if (StringUtils.isBlank(language)) {
                language = getCookieValue(request, LANG);
            }
            if (StringUtils.isBlank(language) || StringUtils.equals(language, "undefined")) {
                language = "en";
            }
            return language;

        }
        return "en";
    }

    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie cookie = org.springframework.web.util.WebUtils.getCookie(request, cookieName);
        if (cookie != null) {
            return cookie.getValue();
        }
        return null;
    }
}
