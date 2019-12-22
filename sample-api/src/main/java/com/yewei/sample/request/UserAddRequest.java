package com.yewei.sample.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserAddRequest {
    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty("年龄")
    private Integer age;
}
