package com.yewei.sample.rest;

import lombok.Data;

import java.util.List;

@Data
public class RubbishResponse {
    private Integer errorCode;
    private List<RubbishEntity> result;
    private String reason;

}
