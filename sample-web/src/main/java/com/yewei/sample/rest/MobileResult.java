package com.yewei.sample.rest;

import lombok.Data;

import java.util.Map;

// 手机归属地
@Data
public class MobileResult {
    private String resultcode;
    private String reason;
    private Map<String,Object> result;
}
