package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            saveOnConditions(resume, index);
        }

    }

    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return getResume(index);
    }

    @Override
    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        } else {
            updateByIndex(resume, index);
        }
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            deleteResume(index);
        }

    }

    @Override
    abstract public Resume[] getAll();

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void clear() {
    }

    abstract protected int getIndex(String uuid);

    abstract protected Resume getResume(int index);

    abstract protected void insertResume(Resume resume, int index);

    abstract protected void deleteResume(int index);

    abstract protected void saveOnConditions(Resume resume, int index);

    abstract protected void updateByIndex(Resume resume, int index);
}
