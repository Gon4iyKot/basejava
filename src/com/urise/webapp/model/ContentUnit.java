package com.urise.webapp.model;

import java.util.Date;
import java.util.Objects;

public class ContentUnit {
    private final String title;
    private final Date startDate;
    private final Date endDate;
    private final String subtitle;
    private final String description;

    public ContentUnit(String title, Date startDate, Date endDate, String subtitle, String description) {
        Objects.requireNonNull(title, "title must not be null");
        Objects.requireNonNull(startDate, "startDate must not be null");
        Objects.requireNonNull(endDate, "endDate must not be null");
        Objects.requireNonNull(subtitle, "subtitle must not be null");
        this.title = title;
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
        return Objects.equals(title, that.title) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(subtitle, that.subtitle) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, startDate, endDate, subtitle, description);
    }

    @Override
    public String toString() {
        return "ContentBlock{" +
                "title='" + title + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", subtitle='" + subtitle + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
