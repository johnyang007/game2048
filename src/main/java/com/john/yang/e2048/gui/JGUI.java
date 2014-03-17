package com.john.yang.e2048.gui;

/**
 * Created by john on 14-3-16.
 */
public class JGUI {


    public static void main(String[] args) {
        int i = 0;
        int j = 0;

        try {
            j++;
            i += addNum();
        } catch (Exception e) {
            System.out.println("over");
        }

        System.out.println("i=" + i + ",j=" + j);

    }

    private static int addNum() throws Exception {
        int i = 3;
        try {
            i++;
            throw new Exception("wrong");
        } catch (Exception e) {
            throw e;
        } finally {
            return i;
        }
    }
}
