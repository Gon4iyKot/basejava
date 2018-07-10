package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    protected void insertResume(Resume resume) {
        int insertIndex = -getIndex(resume.getUuid()) - 1;
        System.arraycopy(storage, insertIndex, storage, insertIndex + 1, size - insertIndex);
        storage[insertIndex] = resume;
    }

    protected int getIndex(String uuid) {
        return Arrays.binarySearch(storage, 0, size, new Resume(uuid));
    }
}