package com.yewei.sample.request;

import com.yewei.common.db.req.PageReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserQueryRequest extends PageReq {
    private String name;
    private String phone;

    private List<Long> ids;
}
