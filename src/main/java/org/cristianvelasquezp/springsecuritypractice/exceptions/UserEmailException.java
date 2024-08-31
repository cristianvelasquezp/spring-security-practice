package org.cristianvelasquezp.springsecuritypractice.exceptions;

public class UserEmailException extends Exception {
    public UserEmailException(String userAlreadyExists) {
        super(userAlreadyExists);
    }
}
