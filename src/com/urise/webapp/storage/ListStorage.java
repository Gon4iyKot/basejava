package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    public final ArrayList<Resume> listStorage = new ArrayList<>();

    @Override
    public List<Resume> getAll() {
        return listStorage;
    }

    @Override
    public int size() {
        return listStorage.size();
    }

    @Override
    public void clear() {
        listStorage.clear();
    }

    @Override
    protected Object getSearchKey(String uuid) {
        for (int i = 0; i < size(); i++) {
            if (listStorage.get(i).getUuid().equals(uuid))
                return i;
        }
        return -1;
    }

    @Override
    protected void deleteResume(Object searchKey) {
        listStorage.remove((int) searchKey);
    }

    @Override
    protected void saveOnConditions(Resume resume, Object searchKey) {
        listStorage.add(resume);
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return listStorage.get((int) searchKey);
    }

    @Override
    protected void rewriteResume(Resume resume, Object searchKey) {
        listStorage.set((int) searchKey, resume);
    }

    @Override
    final protected boolean checkExistence(Object searchKey) {
        return ((Integer) searchKey >= 0);
    }
}
