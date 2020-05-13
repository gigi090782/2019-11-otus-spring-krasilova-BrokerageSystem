package ru.krasilova.otus.spring.brokerage.models.enumeration;

public enum ChannelType {
    VSP("ВСП"),
    ONLINE("ОНлайн");

    private String value;

    ChannelType(String value) {
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