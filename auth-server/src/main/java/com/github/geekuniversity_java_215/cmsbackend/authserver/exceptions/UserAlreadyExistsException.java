package com.github.geekuniversity_java_215.cmsbackend.authserver.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
    }

    public UserAlreadyExistsException(String s) {
        super(s);
    }

    public UserAlreadyExistsException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public UserAlreadyExistsException(Throwable throwable) {
        super(throwable);
    }

    public UserAlreadyExistsException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }

    @Override
    public String getMessage() {
        String result = "User already exists: " + super.getMessage();
        return result;
    }
}
