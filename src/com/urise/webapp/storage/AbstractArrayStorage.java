package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {

    protected static int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    final public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    final public int size() {
        return size;
    }

    @Override
    final public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    final protected void saveOnConditions(Resume resume, int index) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Слишком много резюме", resume.getUuid());
        } else {
            insertResume(resume, index);
            size++;
        }
    }

    @Override
    final protected Resume getResume(int index) {
        return storage[index];
    }

    @Override
    final protected void rewriteResume(Resume resume, int index) {
        storage[index] = resume;
    }

    @Override
    final protected void deleteResume(int index) {
        shiftResumeByType(index);
        storage[size - 1] = null;
        size--;
    }

    abstract protected void insertResume(Resume resume, int index);

    abstract protected void shiftResumeByType(int index);
}