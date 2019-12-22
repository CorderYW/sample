package com.yewei.sample.common.db;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageReq {
    // 页码索引
    private Integer page = 1;

    // 每页显示的条数,默认10条
    private Integer rows = 10;

    // 排序字段
    private String sort;

    // 正序:"asc" 倒序："desc"
    private SortType order;

    // 开始位置
    private long start;

    // 开始时间
    private Date startDate;
    // 结束时间
    private Date endDate;

    public long getStart() {
        return (page - 1) * rows;
    }

    public void setStart(long start) {
        this.start = start;
    }
}