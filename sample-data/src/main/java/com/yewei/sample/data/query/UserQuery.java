package com.yewei.sample.data.query;

import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserQuery {
    private String name;
    private String phone;
    private Date startDate;
    private Date endDate;
}


