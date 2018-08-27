package com.urise.webapp.model;

import java.util.Objects;

public class TextSection extends AbstractSection {
    private String textInfo;

    public TextSection(String personalInfo) {
        Objects.requireNonNull("valuable information should not be empty");
        this.textInfo = personalInfo;
    }

    public String getTextInfo() {
        return textInfo;
    }

    public void setTextInfo(String textInfo) {
        this.textInfo = textInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextSection personal = (TextSection) o;
        return Objects.equals(textInfo, personal.textInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(textInfo);
    }

    @Override
    public String toString() {
        return "TextSection{" +
                "textInfo='" + textInfo + '\'' +
                '}';
    }
}
