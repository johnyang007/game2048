package com.john.yang.e2048.gui;

import com.john.yang.e2048.constants.ActionEnum;
import com.john.yang.e2048.domain.ChessboardPoint;
import com.john.yang.e2048.game.Game;
import com.john.yang.e2048.exception.GameOverException;
import com.john.yang.e2048.interf.DrawPointInterface;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import sun.print.resources.serviceui;

import java.util.Scanner;

/**
 * Created by john on 14-3-17.
 */
public class TextGui {

    private int length;
    private int maxScore;
    private int score;
    private int step;

    public TextGui(int length, int maxScore) {
        this.length = length;
        this.maxScore = maxScore;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        print("请输入命令 w a s d 进行试玩");

        DrawPointInterface drawPointInterface = new DrawPointInterface() {
            @Override
            public void draw(ChessboardPoint chessboardPoint) {
                if (chessboardPoint.isEmpty()) {
                    System.out.print(StringUtils.leftPad("-", 4, " "));
                } else {
                    System.out.print(StringUtils.leftPad(chessboardPoint.getChessman().getNum() + "", 4, " "));
                }

                if (chessboardPoint.getY() == length - 1) {
                    System.out.println();
                }
            }
        };
        Game game = new Game(length, maxScore, drawPointInterface);
        game.start();

        ActionEnum actionEnum = null;
        while (scanner.hasNext()) {
            String cmd = StringUtils.trim(scanner.nextLine());
            if (StringUtils.length(cmd) < 1) {
                continue;
            }
            char c = cmd.charAt(0);

            if (c == 'q') {
                System.out.println("感谢试玩");
                break;
            }


            switch (c) {
                case 'w':
                    actionEnum = ActionEnum.UP;
                    break;
                case 'a':
                    actionEnum = ActionEnum.LEFT;
                    break;
                case 's':
                    actionEnum = ActionEnum.DOWN;
                    break;
                case 'd':
                    actionEnum = ActionEnum.RIGHT;
                    break;
                default:
                    print("请输入 w a s d 控制方向");
            }

            step++;
            score += game.action(actionEnum);
            if (game.isGameOver()) {
                printOver();
                break;
            } else if (game.isWin()) {
                printWin();
                break;
            }

            printGameInfo();
        }
    }

    /**
     * 打印成功
     */
    private void printWin() {
        print("恭喜你，游戏成功");
        printGameInfo();
    }

    private void printGameInfo() {
        print("分数：" + score);
        print("步数：" + step);
    }

    private void printOver() {
        print("很遗憾，游戏结束");
        printGameInfo();
    }

    private void print(String s) {
        System.out.println(s);
    }
}
