package com.john.yang.e2048.interf;

import com.john.yang.e2048.domain.ChessboardPoint;

/**
 * 绘制一个棋点
 * Created by john on 14-3-16.
 */
public interface DrawPointInterface {

    /**
     * 绘制
     */
    public void draw(ChessboardPoint chessboardPoint);
}
