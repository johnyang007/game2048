package com.john.yang.e2048.domain;

import com.john.yang.e2048.constants.ActionEnum;
import com.john.yang.e2048.interf.DrawPointInterface;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 棋盘
 * Created by john on 14-3-16.
 */
public class Chessboard {

    private ChessboardPoint[][] chessboardPoints;
    private DrawPointInterface drawPointInterface;
    private int length;

    public Chessboard(int length, DrawPointInterface drawPointInterface) {
        if (length < 0) {
            throw new IllegalArgumentException("棋盘长度必须大于0");
        }

        this.length = length;
        this.drawPointInterface = drawPointInterface;
        this.chessboardPoints = new ChessboardPoint[length][length];

    }

    /**
     * 初始化棋盘
     */
    public void initChessboard() {
        for (int i = 0; i < chessboardPoints.length; i++) {
            for (int j = 0; j < chessboardPoints[i].length; j++) {
                chessboardPoints[i][j] = new ChessboardPoint(i, j);
            }
        }
    }

    /**
     * 重置棋盘
     */
    public void reset() {
        new ChessboardLooper(new ChessboardLooperCallback() {
            @Override
            public void execute(ChessboardPoint chessboardPoint) {
                if (chessboardPoint != null) {
                    chessboardPoint.clear();
                }
            }
        }).execute();
    }

    /**
     * 随机返回一个空棋点
     * @return
     */
    public ChessboardPoint getOneRandomEmptyChessboardPoint() {
        final List<ChessboardPoint> emptyPointList = new ArrayList<ChessboardPoint>();
        new ChessboardLooper(new ChessboardLooperCallback() {
            @Override
            public void execute(ChessboardPoint chessboardPoint) {
                if (chessboardPoint != null && chessboardPoint.isEmpty()) {
                    emptyPointList.add(chessboardPoint);
                }
            }
        }).execute();

        if (CollectionUtils.isNotEmpty(emptyPointList)) {
            Random random = new Random(emptyPointList.size());
            return emptyPointList.get(random.nextInt(emptyPointList.size()));
        }

        return null;

    }

    public ChessboardPoint[][] getChessboardPoints() {
        return chessboardPoints;
    }

    public DrawPointInterface getDrawPointInterface() {
        return drawPointInterface;
    }

    public void setDrawPointInterface(DrawPointInterface drawPointInterface) {
        this.drawPointInterface = drawPointInterface;
    }

    /**
     * 绘制
     */
    public void draw() {
        if (drawPointInterface != null && ArrayUtils.isNotEmpty(chessboardPoints)) {
            new ChessboardLooper(new ChessboardLooperCallback() {
                @Override
                public void execute(ChessboardPoint chessboardPoint) {
                    drawPointInterface.draw(chessboardPoint);
                }
            }).execute();
        }
    }

    /**
     * 克隆当前棋盘以及棋盘状态
     * @return
     */
    public Chessboard cloneNew() {

        // 新建新棋盘
        final Chessboard newChessboard = new Chessboard(length, drawPointInterface);

        // 循环克隆状态
        new ChessboardLooper(new ChessboardLooperCallback() {
            @Override
            public void execute(ChessboardPoint chessboardPoint) {
                newChessboard.getChessboardPoints()[chessboardPoint.getX()][chessboardPoint.getY()]
                        = chessboardPoint.cloneNew();
            }
        }).execute();

        return newChessboard;
    }

    /**
     * 获取棋盘线列表
     * @return
     */
    public List<ChessboardPoint[]> getChessboardPointLineList(final ActionEnum actionEnum) {
        final List<ChessboardPoint[]> chessboardPointsList = new ArrayList<ChessboardPoint[]>();
        for (int i = 0; i < length; i++) {
            chessboardPointsList.add(new ChessboardPoint[length]);
        }

        new ChessboardLooper(new ChessboardLooperCallback() {
            @Override
            public void execute(ChessboardPoint chessboardPoint) {
                switch (actionEnum) {
                    case UP:
                        chessboardPointsList.get(chessboardPoint.getY())[chessboardPoint.getX()] = chessboardPoint;
                        break;
                    case DOWN:
                        chessboardPointsList.get(chessboardPoint.getY())[length - 1 - chessboardPoint.getX()] = chessboardPoint;
                        break;
                    case LEFT:
                        chessboardPointsList.get(chessboardPoint.getX())[chessboardPoint.getY()] = chessboardPoint;
                        break;
                    case RIGHT:
                        chessboardPointsList.get(chessboardPoint.getX())[length - 1 - chessboardPoint.getY()] = chessboardPoint;
                        break;
                    default:
                        throw new RuntimeException("动作错误");
                }

            }
        }).execute();
        return chessboardPointsList;
    }



    /**
     * 棋盘循环器
     */
    private class ChessboardLooper {
        ChessboardLooperCallback chessboardLooperCallback;

        private ChessboardLooper(ChessboardLooperCallback chessboardLooperCallback) {
            this.chessboardLooperCallback = chessboardLooperCallback;
        }

        public void execute() {
            if (ArrayUtils.isNotEmpty(chessboardPoints) && chessboardLooperCallback != null) {
                for (ChessboardPoint[] chessboardPoints1 : chessboardPoints) {
                    if (ArrayUtils.isNotEmpty(chessboardPoints1)) {
                        for (ChessboardPoint chessboardPoint : chessboardPoints1) {
                            chessboardLooperCallback.execute(chessboardPoint);
                        }
                    }
                }
            }
        }
    }

    public int getLength() {
        return length;
    }


    /**
     * 棋盘循环器回调
     */
    private interface ChessboardLooperCallback {

        void execute(ChessboardPoint chessboardPoint);
    }


}
