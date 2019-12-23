package com.yewei.sample.common.error;


import org.apache.commons.lang3.StringUtils;

/**
 * 系统基本公共异常
 *
 * @author wang-bijie
 */
public enum GeneralCode implements ErrorCode {
    // 100系统异常
    // 001业务代码
    // 001明细代码
    SUCCESS("100000", "正常"),
    SYS_ERROR("1001001", "系统异常"),
    SYS_NOT_SUPPORT("1001002", "不支持的操作"),
    SYS_VALID("1001003", "验证不通过"),
    SYS_ACCESS_LIMITED("1001004", "访问受到限制"),
    SYS_NOT_LOGIN("1001005", "需要登录才能访问"),
    SYS_NOT_EXIST("1001006", "不存在记录"),
    SYS_NOT_VALID_TYPE("1001007", "不支持的验证类型"),
    SYS_BODY_NULL("1001008", "业务主体为空"),
    SYS_ZUUL_ERROR("1001009", "系统正忙，请稍后再试！"),
    SYS_MAINTENANCE("1001010", "系统维护中"),
    SYS_API_MAINTENANCE("1001011", "API维护中"),

    TOKEN_EXPIRE("1002001", "token过期"),
    TOKEN_NOT_EXIST("1002002", "无效的token"),
    GET_REDIS_ERROR("1002003", "获取redis对象异常"),
    GET_REDIS_LOCKED_FAILED("1002004", "获取redis锁失败"),

    COMMON_ERROR("20000", "一般错误"),
    USER_EXIST("2001001", "用户已经存在"),
    USER_ID_ZERO("2001002", "用户id已经用尽"),
    USER_NOT_EXIST("2001003", "用户不存在"),
    USER_PWD_ERROR("2001004", "密码错误"),
    USER_NOT_ACTIVE("2001005", "账号未激活"),
    USER_ACTIVE_CODE_EXPIRED("2001006", "激活码已经过期"),
    USER_ACTIVE_CODE_ERROR("2001007", "激活码错误"),
    USER_ACTIVE_ERROR("2001008", "激活错误"),
    USER_ALREADY_ACTIVATED("2001009", "已经激活"),
    USER_DISABLED("2001010", "账号已经被禁止使用"),
    USER_LOCK("2001011", "账号已经被锁定"),
    USER_EMAIL_USE("2001012", "邮箱已经被使用"),
    ;

    private String code;

    private String message;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    private GeneralCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static GeneralCode findByCode(String code) {
        GeneralCode result = null;
        for (GeneralCode temp : GeneralCode.values()) {
            if (StringUtils.equals(temp.getCode(), code)) {
                result = temp;
                break;
            }
        }
        return result;
    }

}
