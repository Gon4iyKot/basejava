package com.urise.webapp.model;

public enum ContactType {
    ADDRESS("Адрес"),
    TELEPHONE("Номер телефона"),
    EMAIL("АДрес электронной почты"),
    SKYPE("Skype"),
    GITHUB("Аккаунт на GitHub"),
    LINKEDIN("Аккаунт на LinkedIn"),
    STACKOVERFLOW("Аккаунт на Stackoverflow"),
    PERSONALPAGE("Личная страница");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
