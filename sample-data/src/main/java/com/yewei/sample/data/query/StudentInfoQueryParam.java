package com.yewei.sample.data.query;

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
public class StudentInfoQueryParam extends PageReq {
    private String stuName;
    private String phone;
}


