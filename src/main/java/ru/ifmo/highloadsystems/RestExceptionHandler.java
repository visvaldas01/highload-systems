package ru.ifmo.highloadsystems;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.ifmo.highloadsystems.exception.*;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NothingToAddException.class)
    protected ResponseEntity<?> nothingToAddHandler(NothingToAddException ex) {
        return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<?> badCredentialsExceptionHandler() {
        return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Not valid username or password"), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(RegisterException.class)
    protected ResponseEntity<?> registerExceptionHandler(RegisterException ex) {
        return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoPermissionException.class)
    protected ResponseEntity<?> noPermissionHandler(NoPermissionException ex) {
        return new ResponseEntity<>(new AppError(HttpStatus.LOCKED.value(), ex.getMessage()), HttpStatus.LOCKED);
    }

    @ExceptionHandler(AlreadyExistException.class)
    protected ResponseEntity<?> alreadyExistHandler(AlreadyExistException ex) {
        return new ResponseEntity<>(new AppError(HttpStatus.EXPECTATION_FAILED.value(), ex.getMessage()), HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<?> nullPointer() {
        return new ResponseEntity<>(new AppError(HttpStatus.NOT_IMPLEMENTED.value(), "Feature not implemented"), HttpStatus.NOT_IMPLEMENTED);
    }

    @ExceptionHandler(value = {NotImplemented.class})
    protected ResponseEntity<?> notImplemented() {
        return new ResponseEntity<>(new AppError(HttpStatus.NOT_IMPLEMENTED.value(), "Feature not implemented"), HttpStatus.NOT_IMPLEMENTED);
    }
}
