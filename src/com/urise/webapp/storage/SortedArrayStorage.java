package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    protected void insertEl(Resume resume) {
        if (size == 0) {
            storage[0] = resume;
            size++;
        } else {
            int insertIndex = binaryInsert(resume);
            System.arraycopy(storage, insertIndex, storage, insertIndex + 1, size - insertIndex);
            storage[insertIndex] = resume;
            size++;
        }
    }

    protected int getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    protected int binaryInsert(Resume resume) {
        int a = Arrays.binarySearch(storage, 0, size, resume);
        return -a - 1;
    }
}