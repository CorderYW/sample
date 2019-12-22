package com.yewei.sample.common.db;

public enum IsRemoved {
    NO("not removed",0),
    YES("removed",1);
    private String desc;
    private Integer val;

    IsRemoved(String desc, Integer val) {
        this.desc = desc;
        this.val = val;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getVal() {
        return val;
    }

}
