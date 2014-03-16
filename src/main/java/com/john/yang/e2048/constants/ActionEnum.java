package com.john.yang.e2048.constants;

/**
 * 游戏动作枚举
 * Created by john on 14-3-16.
 */
public enum  ActionEnum {
    UP(1, "上"),
    DOWN(1, "下"),
    LEFT(1, "左"),
    RIGHT(1, "右");

    private int code;
    private String desc;

    private ActionEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
