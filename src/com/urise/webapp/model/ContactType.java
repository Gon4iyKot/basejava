package com.urise.webapp.model;

public enum ContactType {
    ADDRESS("Адрес"),
    TELEPHONE("Номер телефона"),
    EMAIL("АДрес электронной почты") {
        @Override
        public String toHtml0(String value) {
            return "<a href='mailto: " + value + "'>" + value + "</a>";
        }
    },
    SKYPE("Skype") {
        @Override
        public String toHtml0(String value) {
            return "<a href='skype: " + value + "'>" + value + "</a>";
        }
    },
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

    public String toHtml0(String value) {
        return title + ": " + value;
    }

    public String toHtml(String value) {
        return (value == null) ? "" : toHtml0(value);
    }
}
