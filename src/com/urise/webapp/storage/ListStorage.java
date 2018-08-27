package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    private ArrayList<Resume> listStorage = new ArrayList<>();

    @Override
    public List<Resume> getAll() {
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
    protected void deleteResume(Integer searchKey) {
        listStorage.remove((int) searchKey);
    }

    @Override
    protected void saveOnConditions(Resume resume, Integer searchKey) {
        listStorage.add(resume);
    }

    @Override
    protected Resume getResume(Integer searchKey) {
        return listStorage.get(searchKey);
    }

    @Override
    protected void rewriteResume(Resume resume, Integer searchKey) {
        listStorage.set(searchKey, resume);
    }

    @Override
    protected boolean checkIfExist(Integer searchKey) {
        return (searchKey >= 0);
    }
}
