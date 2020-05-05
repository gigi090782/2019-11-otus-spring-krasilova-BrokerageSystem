package ru.krasilova.otus.spring.brokerage.rest.exceptions;

public class BadBirthDate extends Exception {
    public BadBirthDate(String message) {
        super(message);
    }

    public BadBirthDate(String message, Throwable cause) {
        super(message, cause);
    }
}

