package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage implements Storage {

    @Override
    public void save(Resume resume) {
        Object searchKey = getIfNotExist(resume.getUuid());
        saveOnConditions(resume, searchKey);
    }

    @Override
    public Resume get(String uuid) {
        Object searchKey = getIfExist(uuid);
        return getResume(searchKey);
    }

    @Override
    public void update(Resume resume) {
        Object searchKey = getIfExist(resume.getUuid());
        rewriteResume(resume, searchKey);
    }

    @Override
    public void delete(String uuid) {
        Object searchKey = getIfExist(uuid);
        deleteResume(searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = getAll();
        Collections.sort(list);
        return list;
    }

    protected final Object getIfNotExist(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (checkIfExist(searchKey)) {
            throw new ExistStorageException(uuid);
        } else {
            return searchKey;
        }

    }

    protected final Object getIfExist(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!checkIfExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        } else {
            return searchKey;
        }
    }

    abstract protected Object getSearchKey(String uuid);

    abstract protected Resume getResume(Object searchKey);

    abstract protected void deleteResume(Object searchKey);

    abstract protected void saveOnConditions(Resume resume, Object searchKey);

    abstract protected void rewriteResume(Resume resume, Object searchKey);

    abstract protected boolean checkIfExist(Object searchKey);

    abstract protected List<Resume> getAll();
}
