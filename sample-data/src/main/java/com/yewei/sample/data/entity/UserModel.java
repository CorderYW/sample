package com.yewei.sample.data.entity;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserModel implements Serializable {
    private static final long serialVersionUID = -5563170097613297020L;
    private Long id;
    private String name;
    private String phone;
    private Integer age;
    private Date createTime;
}
