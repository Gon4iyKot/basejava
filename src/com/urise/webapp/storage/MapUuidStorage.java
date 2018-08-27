package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage<String> {
    private Map<String, Resume> mapStorage = new HashMap<>();

    @Override
    public void clear() {
        mapStorage.clear();
    }

    @Override
    public List<Resume> getAll() {
        return new ArrayList<>(mapStorage.values());
    }

    @Override
    public int size() {
        return mapStorage.size();
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected void deleteResume(String searchKey) {
        mapStorage.remove(searchKey);
    }

    @Override
    protected void saveOnConditions(Resume resume, String searchKey) {
        mapStorage.put(searchKey, resume);
    }

    @Override
    protected Resume getResume(String searchKey) {
        return mapStorage.get(searchKey);
    }

    @Override
    protected void rewriteResume(Resume resume, String searchKey) {
        mapStorage.put(searchKey, resume);
    }

    @Override
    protected boolean checkIfExist(String searchKey) {
        return mapStorage.containsKey(searchKey);
    }
}
