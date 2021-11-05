package ru.sladkkov.ChatSimbirSoft.exception;

public class UserAlreadyCreatedException extends Exception {
    public UserAlreadyCreatedException(String message) {
        super(message);
    }
}
