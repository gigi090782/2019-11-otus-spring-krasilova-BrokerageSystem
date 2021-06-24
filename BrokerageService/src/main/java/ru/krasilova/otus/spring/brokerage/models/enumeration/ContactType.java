package ru.krasilova.otus.spring.brokerage.models.enumeration;

public enum ContactType {
    MOBILE("Мобильный телефон"),
    EMAIL("Email");

    private String value;

    ContactType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}