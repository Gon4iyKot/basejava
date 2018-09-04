package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class OrganisationSection extends AbstractSection {
    private final List<Organization> organizationList;

    public OrganisationSection(List<Organization> organizationList) {
        Objects.requireNonNull(organizationList, "organizationList must not be null");
        this.organizationList = organizationList;
    }

    public List<Organization> getOrganizationList() {
        return organizationList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganisationSection that = (OrganisationSection) o;
        return Objects.equals(organizationList, that.organizationList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizationList);
    }

    @Override
    public String toString() {
        return organizationList.toString();
    }
}
