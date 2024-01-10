package ru.ifmo.highloadsystems.exception;

public class NoPermissionException extends RuntimeException {
    public NoPermissionException(String message) {
        super(message);
    }
}
