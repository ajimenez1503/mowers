package com.example.mowers.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@ControllerAdvice()
public class ExceptionController extends ResponseEntityExceptionHandler {

    /**
     * Exception thrown when {@link org.springframework.validation.annotation.Validated} is used in controller.
     *
     * @param ex
     * @param request
     */
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest request) {
        try {
            List<String> messages = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).toList();
            return new ResponseEntity<>(messages, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(Arrays.asList(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}