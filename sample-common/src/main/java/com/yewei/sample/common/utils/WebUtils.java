package com.yewei.sample.common.utils;

import com.yewei.sample.common.constant.Constant;
import com.yewei.sample.common.enums.TerminalEnum;
import com.yewei.sample.common.models.APIRequest;
import com.yewei.sample.common.models.APIRequestHeader;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@SuppressWarnings("deprecation")
public class WebUtils {
    private final static String UNKNOW_BROWSER = "UnKnownBrowser";
    private final static String UNKNOW_VERSION = "UnKnownVersion";
    private final static String UNKNOW_SYSTEM = "UnKnownSystem";
    public final static String LANG = "lang";

    private static ApplicationContext applicationContext;

    public static String getRequestIp() {
        HttpServletRequest request = getHttpServletRequest();
            String ip = getHeader(Constant.IP_ADDRESS);
        if (StringUtils.isBlank(ip)) {
            ip = IPUtils.getIpAddress(request);
        }
        return ip;
    }

    public static String getHeader(String name) {
        return getHttpServletRequest().getHeader(name);
    }

    public static String getParameter(String name) {
        return getHttpServletRequest().getParameter(name);
    }

    public static HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = null;
        if(servletRequestAttributes != null) {
            request = servletRequestAttributes.getRequest(); 
        }
        return request;
    }

    public static HttpServletResponse getHttpServletResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        WebUtils.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        int max = 50;
        while (applicationContext == null && (max--) > 0) {
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
            }
        }
        return applicationContext;
    }

    public static APIRequestHeader getAPIRequestHeader() {//request 为空返回默认
        HttpServletRequest request = getHttpServletRequest();
        if(request == null) {
            return APIRequest.HEADER_DEFAULT;
        }
        return (APIRequestHeader) request.getAttribute(Constant.API_REQUEST_HEADER);
    }

	public static String getBrowser() {
        HttpServletRequest request = getHttpServletRequest();
        String browser = StringEscapeUtils.escapeHtml4(request.getHeader("User-Agent"));
        if (StringUtils.isBlank(browser)) {
            return "";
        } else if (browser.length() <= 120) {
            return browser;
        } else {
            return browser.substring(0, 120);
        }
    }

    public static String getOsAndBrowserInfo() {
        String browserType = "";
        String browserVersion = "";
        String operation = "";
        HttpServletRequest request = getHttpServletRequest();
        if (getTerminal() != null && getTerminal().getCode().contains("pc")) {
            String pcParam = request.getParameter("pcParam");
            if (StringUtils.isBlank(pcParam)) {
                return "UnKnow Information";
            } else {
                return pcParam;
            }
        }
        if (getTerminal() != null
                && (getTerminal().getCode().equals("android") || getTerminal().getCode().equals("ios"))) {
            String os = request.getHeader("os");
            if (StringUtils.isBlank(os)) {
                os = request.getParameter("os");
                if (StringUtils.isBlank(os)) {
                    return "UnKnow Information";
                } else {
                    return os;
                }
            } else {
                return os;
            }
        }

        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        if (Objects.isNull(userAgent)) {
            return "UnKnow Information";
        }

        if (Objects.isNull(userAgent.getOperatingSystem())
                || StringUtils.isBlank(userAgent.getOperatingSystem().getName())) {
            operation = UNKNOW_BROWSER;
        } else {
            operation = userAgent.getOperatingSystem().getName();
        }

        if (Objects.isNull(userAgent.getBrowserVersion())
                || StringUtils.isBlank(userAgent.getBrowserVersion().getVersion())) {
            browserVersion = UNKNOW_VERSION;
        } else {
            browserVersion = userAgent.getBrowserVersion().getVersion();
        }

        if (Objects.isNull(userAgent.getBrowser()) || StringUtils.isBlank(userAgent.getBrowser().getName())) {
            browserType = UNKNOW_SYSTEM;
        } else {
            browserType = userAgent.getBrowser().getName();
        }

        return operation + " , " + browserType + " " + browserVersion;
    }

    public static TerminalEnum getTerminal() {
        HttpServletRequest request = getHttpServletRequest();
        String clientType = request.getParameter("clientType");
        if (StringUtils.isBlank(clientType)) {
            clientType = request.getHeader("clientType");
        }
        if (StringUtils.isBlank(clientType)) {
            // 保险参数
            if (request.getAttribute("clientTypeTemp") != null) {
                clientType = request.getAttribute("clientTypeTemp").toString();
            }
        }
        if (StringUtils.isNotBlank(clientType)) {
            clientType = clientType.toLowerCase();
        }
        TerminalEnum terminal = TerminalEnum.findByCode(clientType);
        return terminal;
    }
}
