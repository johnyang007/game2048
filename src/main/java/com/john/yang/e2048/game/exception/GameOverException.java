package com.john.yang.e2048.game.exception;

/**
 * Created by john on 14-3-16.
 */
public class GameOverException extends Exception {

    public GameOverException(String msg) {
        super(msg);
    }

    public GameOverException(String message, Throwable cause) {
        super(message, cause);
    }

    public GameOverException(Throwable cause) {
        super(cause);
    }

    public GameOverException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public GameOverException() {
    }
}
