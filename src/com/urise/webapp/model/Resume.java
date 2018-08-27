package com.urise.webapp.model;

import java.util.EnumMap;
import java.util.Objects;
import java.util.UUID;

public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;
    private String fullName;

    public EnumMap<ContactType, String> contact = new EnumMap<>(ContactType.class);
    public EnumMap<SectionType, AbstractSection> section = new EnumMap<>(SectionType.class);

    public String getContacts(ContactType contact) {
        return this.contact.get(contact);
    }

    public AbstractSection getSection(SectionType section) {
        return this.section.get(section);
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) &&
                Objects.equals(fullName, resume.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName);
    }

/*
    @Override
    public String toString() {
        return uuid + '(' + fullName + ')';
    }
*/

    @Override
    public String toString() {
        return "Resume{" +
                "uuid='" + uuid + '\'' +
                ", fullName='" + fullName + '\'' +
                ", contact=" + contact +
                ", section=" + section +
                '}';
    }

    @Override
    public int compareTo(Resume o) {
        int tempResult = fullName.compareTo(o.fullName);
        return tempResult == 0 ? uuid.compareTo(o.uuid) : tempResult;
    }
}
