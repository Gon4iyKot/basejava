package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage {

    protected static int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    final public List<Resume> getAll() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, size));
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
    final protected void saveOnConditions(Resume resume, Object searchKey) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Слишком много резюме", resume.getUuid());
        } else {
            insertResume(resume, (int) searchKey);
            size++;
        }
    }

    @Override
    final protected Resume getResume(Object searchKey) {
        return storage[(int) searchKey];
    }

    @Override
    final protected void rewriteResume(Resume resume, Object searchKey) {
        storage[(int) searchKey] = resume;
    }

    @Override
    final protected void deleteResume(Object searchKey) {
        shiftResume((int) searchKey);
        storage[size - 1] = null;
        size--;
    }

    @Override
    final protected boolean checkExistence(Object searchKey) {
        return ((Integer) searchKey >= 0);
    }

    abstract protected void insertResume(Resume resume, int index);

    abstract protected void shiftResume(int index);
}