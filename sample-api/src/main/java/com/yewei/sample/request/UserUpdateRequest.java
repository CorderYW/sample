package com.yewei.sample.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserUpdateRequest extends  UserAddRequest{
    private Long id;
}
