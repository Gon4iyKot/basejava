package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void save(Resume resume) {
        String tempUuid = getUuidFromResume(resume);
        int index = getIndex(tempUuid);
        if (index >= 0) {
            throw new ExistStorageException(tempUuid);
        } else {
            saveOnConditions(resume, index);
        }

    }

    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        throwNotExistStorageExceptionIf(index, uuid);
        return getResume(index);
    }

    @Override
    public void update(Resume resume) {
        String tempUuid = getUuidFromResume(resume);
        int index = getIndex(tempUuid);
        throwNotExistStorageExceptionIf(index, tempUuid);
        updateByIndex(resume, index);
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        throwNotExistStorageExceptionIf(index, uuid);
        deleteResume(index);
    }

    protected final String getUuidFromResume(Resume resume) {
        return resume.getUuid();
    }

    protected final void throwNotExistStorageExceptionIf(int index, String uuid) {
        if (index < 0)
            throw new NotExistStorageException(uuid);
        }

    abstract protected int getIndex(String uuid);

    abstract protected Resume getResume(int index);

    abstract protected void deleteResume(int index);

    abstract protected void saveOnConditions(Resume resume, int index);

    abstract protected void updateByIndex(Resume resume, int index);
}
