package com.john.yang.e2048.gui;

import com.john.yang.e2048.constants.ActionEnum;
import com.john.yang.e2048.domain.ChessboardPoint;
import com.john.yang.e2048.game.Game;
import com.john.yang.e2048.game.exception.GameOverException;
import com.john.yang.e2048.interf.DrawPointInterface;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Scanner;

/**
 * Created by john on 14-3-17.
 */
public class TextGui {

    private int length;

    public TextGui(int length) {
        this.length = length;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        print("请输入命令 w a s d 进行试玩");

        DrawPointInterface drawPointInterface = new DrawPointInterface() {
            @Override
            public void draw(ChessboardPoint chessboardPoint) {
                if (chessboardPoint.isEmpty()) {
                    System.out.print(StringUtils.leftPad("-", 2, " "));
                } else {
                    //print(chessboardPoint.toString());
                    System.out.print(StringUtils.leftPad(chessboardPoint.getChessman().getNum() + "", 2, " "));
                }

                if (chessboardPoint.getY() == length - 1) {
                    System.out.println();
                }
            }
        };
        Game game = new Game(length, drawPointInterface);
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

            try {
                game.action(actionEnum);
            } catch (GameOverException e) {
                print("游戏结束");
                break;
            }
        }
    }

    private void print(String s) {
        System.out.println(s);
    }
}
