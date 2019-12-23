package com.yewei.sample.common.db;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 6514352340382651296L;

    private Long total;
    private List<T> records;
    public static <T> PageResult<T> instance(Long total, List records) {
        PageResult<T> pageRespVo = new PageResult<>();
        pageRespVo.setTotal(total);
        pageRespVo.setRecords(records);
        return pageRespVo;
    }

}
