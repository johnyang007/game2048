package com.john.yang.e2048.game;

import com.john.yang.e2048.constants.ActionEnum;
import com.john.yang.e2048.domain.Chessboard;
import com.john.yang.e2048.domain.ChessboardPoint;
import com.john.yang.e2048.domain.Chessman;
import com.john.yang.e2048.exception.GameOverException;
import com.john.yang.e2048.interf.DrawPointInterface;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.util.List;

/**
 * 回合
 * Created by john on 14-3-16.
 */
public class Game {

    private int maxScore; // 最大分数
    private Chessboard chessboard;
    private int chessboardLength;

    private DrawPointInterface drawPointInterface;

    private boolean isGameOver;
    private boolean isWin;


    public Game(int chessboardLength, int maxScore) {
        this(chessboardLength, maxScore, null);

    }

    public Game(int chessboardLength, int maxScore, DrawPointInterface drawPointInterface) {
        isGameOver = false;
        isWin = false;
        this.chessboardLength = chessboardLength;
        this.drawPointInterface = drawPointInterface;
        this.maxScore = maxScore;
        this.chessboard = new Chessboard(this.chessboardLength, drawPointInterface);

        this.chessboard.initChessboard();
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public boolean isWin() {
        return isWin;
    }

    /**
     * 启动
     */
    public void start() {
        isGameOver = false;
        isWin = false;

        chessboard.getOneRandomEmptyChessboardPoint().setChessman(new Chessman());
        chessboard.getOneRandomEmptyChessboardPoint().setChessman(new Chessman());

        draw();
    }

    /**
     * 结束
     */
    public void stop() {
        chessboard.reset();
        draw();
    }


    /**
     * 重新开始
     */
    public void restart() {
        stop();
        start();
    }

    public int action(ActionEnum actionEnum) {
        int score = 0;
        if (actionEnum == null) {
            throw new RuntimeException("不被支持的动作");
        }

        // 计算走一步
        score = calcOneStep(chessboard, actionEnum);

        // 随机添加棋点
        ChessboardPoint chessboardPoint = chessboard.getOneRandomEmptyChessboardPoint();

        if (chessboardPoint != null) {
            chessboardPoint.setChessman(new Chessman());

            // 判断是否游戏结束
            checkIsGameOver();
        } else {
            isGameOver = true;
        }
        // 重绘
        draw();
        return score;
    }

    /**
     * 计算棋盘和移动
     *
     * @param tempChessboard
     * @param actionEnum
     */
    private int calcOneStep(Chessboard tempChessboard, ActionEnum actionEnum) {
        /**
         * 获取棋盘线列表
         */
        List<ChessboardPoint[]> chessboardPointLineList = tempChessboard.getChessboardPointLineList(actionEnum);

        // 计算棋盘线
        return calculateChessboardPointLineList(chessboardPointLineList);
    }

    /**
     * 判断是否游戏结束
     */
    private void checkIsGameOver() {
        if (chessboard.getOneRandomEmptyChessboardPoint() != null) {
            return;
        }

        Chessboard tempChessboard = chessboard.cloneNew();
        for (ActionEnum actionEnum : ActionEnum.values()) {
            // 模拟走一步
            calcOneStep(tempChessboard, actionEnum);

            if (tempChessboard.getOneRandomEmptyChessboardPoint() != null) {
                return;
            }
        }


        if (tempChessboard.getOneRandomEmptyChessboardPoint() == null) {
            draw();
            isGameOver = true;
        }
    }

    /**
     * 计算棋盘线的移动
     *
     * @param chessboardPointLineList
     */
    private int calculateChessboardPointLineList(List<ChessboardPoint[]> chessboardPointLineList) {
        int score = 0;
        if (CollectionUtils.isNotEmpty(chessboardPointLineList)) {
            for (ChessboardPoint[] chessboardPoints : chessboardPointLineList) {
                score += calculateChessboardPointLine(chessboardPoints);
            }
        }

        return score;
    }

    /**
     * 计算棋盘线移动
     *
     * @param points
     */
    private int calculateChessboardPointLine(ChessboardPoint[] points) {
        int score = 0;
        if (ArrayUtils.isNotEmpty(points)) {
            int resultIndex = 0;
            boolean isNeedMatch = false;// 是否结果点需要匹配。每个点只能匹配一次

            for (int i = 0; i < points.length; i++) {
                ChessboardPoint currentPoint = points[i];
                if (currentPoint.isEmpty()) {
                    continue;
                }


                // 判断是否结果棋点需要匹配。
                if (isNeedMatch) {

                    // 如果结果点棋子和当前点棋子数字相同，就合并。结果点坐标向前移动一个位置。设置匹配状态为 不需要匹配
                    if (points[resultIndex].getChessman().getNum() == currentPoint.getChessman().getNum()) {
                        score += mergeChessman(currentPoint, points[resultIndex]);

                        resultIndex++;
                        isNeedMatch = false;
                    } else {

                        // 如果不想等的棋子数字。就移动当前点到结果棋点的下一个位置，设置状态为需要匹配
                        resultIndex++;
                        moveChessman(currentPoint, points[resultIndex]);
                        isNeedMatch = true;
                    }
                } else {

                    // 如果结果棋点不需要匹配，移动当前点棋子到结果棋点上
                    // 设置结果点状态为需要匹配
                    moveChessman(currentPoint, points[resultIndex]);

                    isNeedMatch = true;
                }
            }
        }

        return score;
    }

    /**
     * 合并棋子
     *
     * @param startPoint
     * @param endPoint
     */
    private int mergeChessman(ChessboardPoint startPoint, ChessboardPoint endPoint) {
        int score = 0;
        if (startPoint.isSamePosition(endPoint)) {
            return score;
        }

        if (startPoint.isNotEmpty() && endPoint.isNotEmpty()) {
            Chessman endPointChessman = endPoint.getChessman();
            score = endPointChessman.getNum() + startPoint.getChessman().getNum();
            if (score >= maxScore) {
                isWin = true;
            }
            endPointChessman.setNum(score);
        }

        startPoint.clear();

        return score;
    }

    /**
     * 移动棋子
     *
     * @param startPoint
     * @param endPoint
     */
    private void moveChessman(ChessboardPoint startPoint, ChessboardPoint endPoint) {
        if (startPoint.isSamePosition(endPoint)) {
            return;
        }

        if (startPoint.isNotEmpty()) {
            endPoint.setChessman(startPoint.getChessman());
        }

        startPoint.clear();
    }

    /**
     * 绘制
     */
    public void draw() {
        chessboard.draw();
    }

    public Chessboard getChessboard() {
        return chessboard;
    }

    public void setChessboard(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public int getChessboardLength() {
        return chessboardLength;
    }

    public void setChessboardLength(int chessboardLength) {
        this.chessboardLength = chessboardLength;
    }

    public DrawPointInterface getDrawPointInterface() {
        return drawPointInterface;
    }

    public void setDrawPointInterface(DrawPointInterface drawPointInterface) {
        this.drawPointInterface = drawPointInterface;
    }
}
