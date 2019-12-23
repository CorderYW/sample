package com.yewei.sample.data.query;

import com.yewei.sample.common.db.PageReq;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserQueryParam extends PageReq {
    private String name;
    private String phone;
    private Date startDate;
    private Date endDate;
    private List<Long> ids;
}


