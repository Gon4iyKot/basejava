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
//        LOG.info("Save" + resume);
        SK searchKey = getIfNotExist(resume.getUuid());
        doSave(resume, searchKey);
    }

    @Override
    public final Resume get(String uuid) {
//        LOG.info("Get" + uuid);
        SK searchKey = getIfExist(uuid);
        return doGet(searchKey);
    }

    @Override
    public final void update(Resume resume) {
//        LOG.info("Update" + resume);
        SK searchKey = getIfExist(resume.getUuid());
        doUpdate(resume, searchKey);
    }

    @Override
    public final void delete(String uuid) {
//        LOG.info("Delete" + uuid);
        SK searchKey = getIfExist(uuid);
        doDelete(searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
//        LOG.info("GetAllSorted");
        List<Resume> list = doCopyAll();
        Collections.sort(list);
        return list;
    }

    private SK getIfNotExist(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
//            LOG.warning("Резюме " + uuid + " не существует, попробуйте в другой раз");
            throw new ExistStorageException(uuid);
        } else {
            return searchKey;
        }

    }

    private SK getIfExist(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
//            LOG.warning("Резюме " + uuid + " уже существует");
            throw new NotExistStorageException(uuid);
        } else {
            return searchKey;
        }
    }

    abstract protected SK getSearchKey(String uuid);

    abstract protected Resume doGet(SK searchKey);

    abstract protected void doDelete(SK searchKey);

    abstract protected void doSave(Resume resume, SK searchKey);

    abstract protected void doUpdate(Resume resume, SK searchKey);

    abstract protected boolean isExist(SK searchKey);

    abstract protected List<Resume> doCopyAll();
}
