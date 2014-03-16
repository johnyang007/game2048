package com.john.yang.e2048.domain;

/**
 * 棋子
 * Created by john on 14-3-16.
 */
public class Chessman {

    public static int DEFAULT_NUM = 2;

    private int num;

    public Chessman(int num) {
        this.num = num;
    }

    public Chessman() {
        this(DEFAULT_NUM);
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    /**
     * 获取棋子的颜色
     * @return
     */
    public int getColor() {
        return num;
    }

    @Override
    public String toString() {
        return "Chessman{" +
                "num=" + num +
                '}';
    }
}
