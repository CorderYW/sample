package com.yewei.sample.request;

import com.yewei.common.db.req.PageReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentInfoQueryRequest extends PageReq {
    private String name;
    private String phone;
}
