package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization {
    private final Link homePage;

    private final List<PeriodOf> listOfPeriods = new ArrayList<>();

    public Organization(String name, String url, LocalDate startDate, LocalDate endDate, String title, String description) {
        Objects.requireNonNull(name, "title must not be null");
        this.homePage = new Link(name, url);
        this.listOfPeriods.add(new PeriodOf(startDate, endDate, title, description));
    }

    public void addPeriodOf(LocalDate startDate, LocalDate endDate, String title, String description) {
        this.listOfPeriods.add(new PeriodOf(startDate, endDate, title, description));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(homePage, that.homePage) &&
                Objects.equals(listOfPeriods, that.listOfPeriods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homePage, listOfPeriods);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "homePage=" + homePage +
                ", listOfPeriods=" + listOfPeriods +
                '}';
    }

    private class PeriodOf {
        private final LocalDate startDate;
        private final LocalDate endDate;
        private final String title;
        private final String description;

        public PeriodOf(LocalDate startDate, LocalDate endDate, String title, String description) {
            Objects.requireNonNull(startDate, "startDate must not be null");
            Objects.requireNonNull(endDate, "endDate must not be null");
            Objects.requireNonNull(title, "title must not be null");
            this.startDate = startDate;
            this.endDate = endDate;
            this.title = title;
            this.description = description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PeriodOf periodOf = (PeriodOf) o;
            return Objects.equals(startDate, periodOf.startDate) &&
                    Objects.equals(endDate, periodOf.endDate) &&
                    Objects.equals(title, periodOf.title) &&
                    Objects.equals(description, periodOf.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(startDate, endDate, title, description);
        }

        @Override
        public String toString() {
            return "PeriodOf{" +
                    "startDate=" + startDate +
                    ", endDate=" + endDate +
                    ", title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }
}
