package com.yewei.sample.request;

import com.yewei.sample.common.db.Page;
import com.yewei.sample.common.db.PageReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserQueryRequest extends PageReq {
    private String name;
    private String phone;
}
