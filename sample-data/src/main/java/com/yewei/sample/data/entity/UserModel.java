package com.yewei.sample.data.entity;

import lombok.*;

import java.util.Date;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    private Integer id;
    private String name;
    private String phone;
    private Integer age;
    private Date createTime;
}
