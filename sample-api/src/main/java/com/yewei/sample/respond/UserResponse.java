package com.yewei.sample.respond;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class UserResponse {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty("年龄1")
    private Integer age;

    @ApiModelProperty("创建时间")
    private Date createTime;
}
