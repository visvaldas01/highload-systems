package ru.ifmo.highloadsystems;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.ifmo.highloadsystems.exception.AppError;
import ru.ifmo.highloadsystems.exception.NothingToAddException;
import ru.ifmo.highloadsystems.exception.RegisterException;

@ControllerAdvice
public class RestExceptionHandler
        extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = { NothingToAddException.class })
    protected ResponseEntity<?> nothingToAddHandler(NothingToAddException ex, WebRequest request) {
        return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { BadCredentialsException.class })
    protected ResponseEntity<?> badCredentialsExceptionHandler(BadCredentialsException ex, WebRequest request) {
        return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Not valid username or password"), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = { RegisterException.class })
    protected ResponseEntity<?> registerExceptionHandler(RegisterException ex, WebRequest request) {
        return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
