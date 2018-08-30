package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection {
    private final List<String> listOfValues;

    public ListSection(List<String> listOfValues) {
        Objects.requireNonNull(listOfValues, "list must not be null");
        this.listOfValues = listOfValues;
    }

    public List<String> getListOfValues() {
        return listOfValues;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return Objects.equals(listOfValues, that.listOfValues);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listOfValues);
    }

    @Override
    public String toString() {
        return listOfValues.toString();
    }
}
