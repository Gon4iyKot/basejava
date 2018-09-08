package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    private ArrayList<Resume> listStorage = new ArrayList<>();

    @Override
    public List<Resume> doCopyAll() {
        return new ArrayList<>(listStorage);
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
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < size(); i++) {
            if (listStorage.get(i).getUuid().equals(uuid))
                return i;
        }
        return -1;
    }

    @Override
    protected void doDelete(Integer searchKey) {
        listStorage.remove((int) searchKey);
    }

    @Override
    protected void doSave(Resume resume, Integer searchKey) {
        listStorage.add(resume);
    }

    @Override
    protected Resume doGet(Integer searchKey) {
        return listStorage.get(searchKey);
    }

    @Override
    protected void doUpdate(Resume resume, Integer searchKey) {
        listStorage.set(searchKey, resume);
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return (searchKey >= 0);
    }
}
