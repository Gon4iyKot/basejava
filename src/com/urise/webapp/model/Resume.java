package com.urise.webapp.model;

import java.util.UUID;

/**
 * com.urise.webapp.model.com.urise.webapp.model.Resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;
    private String fullName;

    public Resume() {
        this(UUID.randomUUID().toString(), "fakeResume");
    }

    public Resume(String fullName) {
        this.uuid = UUID.randomUUID().toString();
        this.fullName = fullName;
    }

    public Resume(String uuid, String fullName) {
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

        if (uuid.equals(resume.uuid)) {
            return fullName.equals(resume.fullName);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public String toString() {
        return uuid;
    }

    @Override
    public int compareTo(Resume o) {
        int tempResult = fullName.compareTo(o.fullName);
        if (tempResult == 0){
            return uuid.compareTo(o.uuid);
        }
        return tempResult;
    }
}
