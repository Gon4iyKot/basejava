package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    @Override
    public final void save(Resume resume) {
        LOG.info("Save" + resume);
        SK searchKey = getIfNotExist(resume.getUuid());
        saveOnConditions(resume, searchKey);
    }

    @Override
    public final Resume get(String uuid) {
        LOG.info("Get" + uuid);
        SK searchKey = getIfExist(uuid);
        return getResume(searchKey);
    }

    @Override
    public final void update(Resume resume) {
        LOG.info("Update" + resume);
        SK searchKey = getIfExist(resume.getUuid());
        rewriteResume(resume, searchKey);
    }

    @Override
    public final void delete(String uuid) {
        LOG.info("Delete" + uuid);
        SK searchKey = getIfExist(uuid);
        deleteResume(searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("GetAllSorted");
        List<Resume> list = getAll();
        Collections.sort(list);
        return list;
    }

    private SK getIfNotExist(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (checkIfExist(searchKey)) {
            LOG.warning("Резюме " + uuid + " не существует, попробуйте в другой раз");
            throw new ExistStorageException(uuid);
        } else {
            return searchKey;
        }

    }

    private SK getIfExist(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!checkIfExist(searchKey)) {
            LOG.warning("Резюме " + uuid + " уже существует");
            throw new NotExistStorageException(uuid);
        } else {
            return searchKey;
        }
    }

    abstract protected SK getSearchKey(String uuid);

    abstract protected Resume getResume(SK searchKey);

    abstract protected void deleteResume(SK searchKey);

    abstract protected void saveOnConditions(Resume resume, SK searchKey);

    abstract protected void rewriteResume(Resume resume, SK searchKey);

    abstract protected boolean checkIfExist(SK searchKey);

    abstract protected List<Resume> getAll();
}
