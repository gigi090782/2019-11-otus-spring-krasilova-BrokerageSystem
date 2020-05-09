package ru.krasilova.otus.spring.brokerage.models.enumeration;

public enum MarketPlaceType {
    Fond("Фондовый рынок"),
    Curr("Валютный рынок"),
    Term("Срочный рынок");

    private String value;

    MarketPlaceType(String value) {
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