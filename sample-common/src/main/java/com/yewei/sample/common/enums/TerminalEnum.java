package com.yewei.sample.common.enums;

public enum TerminalEnum implements BaseEnum {

    WEB("web", "web"),
    // 移动端 无法区分是ios 还是 android是显示
    MOBILE("mobile", "mobile"),
    IOS("ios", "ios"),
    ANDROID("android", "android"),
    ANDROIDINFO("androidinfo", "androidinfo"),
    PC("pc", "pc"),
    H5("h5", "h5"),
    MAC("mac", "mac"),
    OTHER("other", "其他"),;
    private String code;

    private String desc;

    TerminalEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    public static TerminalEnum findByCode(String code) {
        TerminalEnum[] terminalEnums = TerminalEnum.values();
        for (TerminalEnum terminalEnum : terminalEnums) {
            if (terminalEnum.getCode().equals(code)) {
                return terminalEnum;
            }
        }
        return null;
    }

}
