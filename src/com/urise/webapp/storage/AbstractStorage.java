package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void save(Resume resume) {
        int index = checkIfExist(resume);
        saveOnConditions(resume, index);
    }

    @Override
    public Resume get(String uuid) {
        int index = checkIfNotExist(uuid);
        return getResume(index);
    }

    @Override
    public void update(Resume resume) {
        int index = checkIfNotExist(resume.getUuid());
        rewriteResume(resume, index);
    }

    @Override
    public void delete(String uuid) {
        int index = checkIfNotExist(uuid);
        deleteResume(index);
    }

    protected final int checkIfExist(Resume resume) {
        String tempUuid = resume.getUuid();
        int index = getIndex(tempUuid);
        if (index >= 0) {
            throw new ExistStorageException(tempUuid);
        } else {
            return index;
        }

    }

    protected final int checkIfNotExist(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            return index;
        }
    }

    abstract protected int getIndex(String uuid);

    abstract protected Resume getResume(int index);

    abstract protected void deleteResume(int index);

    abstract protected void saveOnConditions(Resume resume, int index);

    abstract protected void rewriteResume(Resume resume, int index);


}
