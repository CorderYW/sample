package com.yewei.sample.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserUpdateRequest extends  UserAddRequest{
    @ApiModelProperty("id")
    private Integer id;
}
