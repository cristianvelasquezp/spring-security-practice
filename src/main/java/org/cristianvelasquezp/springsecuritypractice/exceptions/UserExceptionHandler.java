package org.cristianvelasquezp.springsecuritypractice.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.cristianvelasquezp.springsecuritypractice.models.UserErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(UserEmailException.class)
    public ResponseEntity<UserErrorResponse> handleUserEmailException(UserEmailException ex) {
        UserErrorResponse error = new UserErrorResponse();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(ex.getMessage());
        error.setTimeStamp(System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<UserErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        UserErrorResponse error = new UserErrorResponse();
        String message = ex.getConstraintViolations().stream().findFirst().get().getMessage();
        String property = ex.getConstraintViolations().stream().findFirst().get().getPropertyPath().toString();
        String messageTemplate = ex.getConstraintViolations().stream().findFirst().get().getMessageTemplate();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        if (property.equals("email") && messageTemplate.equals("{jakarta.validation.constraints.Pattern.message}")) {
            message = "Invalid email";
        }
        error.setMessage(message);
        System.out.println(message);
        System.out.println(ex.getConstraintViolations());
        System.out.println(ex.getConstraintViolations().stream().findFirst().get().getPropertyPath());
        error.setTimeStamp(System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

}
