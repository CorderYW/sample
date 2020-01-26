package com.yewei.sample.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StudentInfoAddRequest {
    @ApiModelProperty("姓名")
    private String stuName;

    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty("状态")
    private Integer stuStatus;
    @ApiModelProperty("openId")
    private String openId;
}
