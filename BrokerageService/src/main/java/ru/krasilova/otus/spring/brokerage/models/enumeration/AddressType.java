package ru.krasilova.otus.spring.brokerage.models.enumeration;

public enum AddressType {
    POST("Почтовый"),
    REGISTRATION("Регистрации");

    private String value;

    AddressType(String value) {
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