package com.john.yang.e2048.domain;

import com.john.yang.e2048.interf.DrawPointInterface;

/**
 * 棋盘点
 * Created by john on 14-3-16.
 */
public class ChessboardPoint {

    private int x;
    private int y;

    private Chessman chessman;

    public ChessboardPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Chessman getChessman() {
        return chessman;
    }

    public void setChessman(Chessman chessman) {
        this.chessman = chessman;
    }

    /**
     * 是否为空
     * @return
     */
    public boolean isEmpty() {
        return chessman == null;
    }

    public void clear() {
        this.chessman = null;
    }

    /**
     * 非空
     * @return
     */
    public boolean isNotEmpty() {
        return !isEmpty();
    }

    /**
     * 克隆新点
     * @return
     */
    public ChessboardPoint cloneNew() {
        ChessboardPoint newPoint = new ChessboardPoint(this.x, this.y);
        if (this.chessman != null) {
            newPoint.setChessman(new Chessman(this.chessman.getNum()));
        }
        return newPoint;
    }


    @Override
    public String toString() {
        return "ChessboardPoint{" +
                "x=" + x +
                ", y=" + y +
                ", chessman=" + chessman +
                '}';
    }
}
