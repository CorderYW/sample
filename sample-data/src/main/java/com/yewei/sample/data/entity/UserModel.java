package com.yewei.sample.data.entity;

import lombok.*;

import java.util.Date;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    private int id;
    private String name;
    private String phone;
    private int age;
    private Date createTime;
}
