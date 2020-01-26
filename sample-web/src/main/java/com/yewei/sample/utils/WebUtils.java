package com.yewei.sample.utils;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

@Log4j2
public class WebUtils {
    private static final Pattern IPV4_PATTERN =
            Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");
    private static final Pattern IPV6_STD_PATTERN = Pattern.compile("^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$");
    private static final Pattern IPV6_HEX_COMPRESSED_PATTERN = Pattern
            .compile("^((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)$");
    public static boolean isIPv6StdAddress(final String input) {
        return IPV6_STD_PATTERN.matcher(input).matches();
    }

    public static boolean isIPv6HexCompressedAddress(final String input) {
        return IPV6_HEX_COMPRESSED_PATTERN.matcher(input).matches();
    }

    private static final String LOCALHOST = "127.0.0.1";
    public static final String X_ORIGIN_FORWARD_FOR = "X-Origin-Forwarded-For";
    public static final String X_FORWARD_FOR = "X-Forwarded-For";
    public static final String UNKNOWN = "unknown";

    public static String getRequestIp() {
        HttpServletRequest request = getHttpServletRequest();
        String ip = getHeader("ip_address");
        if (StringUtils.isBlank(ip)) {
            ip = getIpAddress(request);
        }
        return ip;
    }
    public static String getIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader(X_ORIGIN_FORWARD_FOR);
        if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader(X_FORWARD_FOR);
        }
        if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (LOCALHOST.equals(ipAddress) || "0:0:0:0:0:0:0:1".equals(ipAddress)) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                    ipAddress = inet.getHostAddress();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }

            }
        }
        if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            throw new IllegalArgumentException("获取不到客户端IP请检查网络配置");
        }
        return getRealIp(ipAddress,request);
    }
    private static String getRealIp(String ipAddress, HttpServletRequest request) {
        String realIp = "";
        if (ipAddress == null) {
            return realIp;
        }
        log.debug("ip addr: {},X-Origin-Forwarded-For={}", ipAddress, request.getHeader("X-Origin-Forwarded-For"));
        String[] ips = ipAddress.split(",");
        int ipIndex = 2;
        if (ips.length >= ipIndex) {
            realIp = ips[ips.length - ipIndex];
        } else {
            realIp = ips[0];
        }
        realIp = realIp.trim();
        if (!isIPv4Address(realIp) && !isIPv6StdAddress(realIp) && !isIPv6HexCompressedAddress(realIp)) {
            log.error("IP格式错误 {} {} ", ipAddress, realIp);
            realIp = "";
        }
        return realIp;
    }
    public static boolean isIPv4Address(final String input) {
        return IPV4_PATTERN.matcher(input).matches();
    }

    public static String getHeader(String name) {
        return getHttpServletRequest().getHeader(name);
    }

    public static HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = null;
        if(servletRequestAttributes != null) {
            request = servletRequestAttributes.getRequest();
        }
        return request;
    }


}
