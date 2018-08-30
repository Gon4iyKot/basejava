package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class ContentUnit {
    private final Link homePage;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String subtitle;
    private final String description;

    public ContentUnit(String name, String url, LocalDate startDate, LocalDate endDate, String subtitle, String description) {
        Objects.requireNonNull(name, "title must not be null");
        Objects.requireNonNull(startDate, "startDate must not be null");
        Objects.requireNonNull(endDate, "endDate must not be null");
        Objects.requireNonNull(subtitle, "subtitle must not be null");
        this.homePage = new Link(name, url);
        this.startDate = startDate;
        this.endDate = endDate;
        this.subtitle = subtitle;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContentUnit that = (ContentUnit) o;
        return Objects.equals(homePage, that.homePage) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(subtitle, that.subtitle) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homePage, startDate, endDate, subtitle, description);
    }

    @Override
    public String toString() {
        return "ContentUnit{" +
                "title='" + homePage + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", subtitle='" + subtitle + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
