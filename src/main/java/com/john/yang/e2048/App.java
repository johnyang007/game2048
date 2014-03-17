package com.john.yang.e2048;

import com.john.yang.e2048.gui.GameGui;
import com.john.yang.e2048.gui.TextGui;

/**
 * Created by john on 14-3-16.
 */
public class App {

    public static void main(String[] args) {
        System.out.println("2048");

        new TextGui(4, 2048).start();

    }
}
