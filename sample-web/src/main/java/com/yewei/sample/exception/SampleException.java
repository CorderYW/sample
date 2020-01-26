package com.yewei.sample.exception;

import com.yewei.common.error.ErrorCode;

public enum SampleException implements ErrorCode {

    USER_NOT_EXIST("083001", "支付方式不存在"),
    TRY_TOMORROW("083002", "您已经提交过"),
    NAME_ERROR("083002", "输入的名字不对"),

        ;

    private String code;
    private String message;
    private SampleException(String code, String message) {
        this.code = code;
        this.message = message;
    }
    @Override
    public String getCode() {
        return null;
    }

    @Override
    public String getMessage() {
        return null;
    }
}
