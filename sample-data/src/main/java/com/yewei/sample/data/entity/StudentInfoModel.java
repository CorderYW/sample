package com.yewei.sample.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

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
    private Boolean filled=true;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentInfoModel that = (StudentInfoModel) o;
        return stuName.equals(that.stuName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stuName);
    }
}
