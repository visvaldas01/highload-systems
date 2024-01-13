package ru.ifmo.user.exception;

public class NothingToAddException extends RuntimeException {
    public NothingToAddException(String message) {
        super(message);
    }
}
