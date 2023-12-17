package ru.ifmo.highloadsystems;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.ifmo.highloadsystems.exception.*;

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

    @ExceptionHandler(value = { NoPermissionException.class })
    protected ResponseEntity<?> noPermissionHandler(NoPermissionException ex, WebRequest request) {
        return new ResponseEntity<>(new AppError(HttpStatus.LOCKED.value(), ex.getMessage()), HttpStatus.LOCKED);
    }

    @ExceptionHandler(value = { AlreadyExistException.class })
    protected ResponseEntity<?> alreadyExistHandler(AlreadyExistException ex, WebRequest request) {
        return new ResponseEntity<>(new AppError(HttpStatus.EXPECTATION_FAILED.value(), ex.getMessage()), HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(value = { NullPointerException.class })
    protected ResponseEntity<?> notImplemented(NullPointerException ex, WebRequest request) {
        return new ResponseEntity<>(new AppError(HttpStatus.NOT_IMPLEMENTED.value(), "Feature not implemented"), HttpStatus.NOT_IMPLEMENTED);
    }
}
