package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage {

    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public List<Resume> getAll() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected final void saveOnConditions(Resume resume, Object searchKey) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Слишком много резюме", resume.getUuid());
        } else {
            insertResume(resume, (Integer) searchKey);
            size++;
        }
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return storage[(Integer) searchKey];
    }

    @Override
    protected void rewriteResume(Resume resume, Object searchKey) {
        storage[(Integer) searchKey] = resume;
    }

    @Override
    protected final void deleteResume(Object searchKey) {
        shiftResume((Integer) searchKey);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected boolean checkIfExist(Object searchKey) {
        return ((Integer) searchKey >= 0);
    }

    abstract protected void insertResume(Resume resume, int index);

    abstract protected void shiftResume(int index);
}