package com.yewei.sample.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StudentInfoModel implements Serializable {
    private static final long serialVersionUID = -5563170097613297020L;
    private Long id;
    private String stuName;
    private String phone;
    private String openId;
    private Integer stuStatus;
    private Date createDate;
}
