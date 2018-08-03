package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage{
    public final ArrayList<Resume> listStorage = new ArrayList<>();

    @Override
    public Resume[] getAll() {
        return listStorage.toArray(new Resume[0]);
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
    protected int getIndex(String uuid) {
        return listStorage.indexOf(new Resume(uuid));
    }

    @Override
    protected void deleteResume(int index) {
        listStorage.remove(index);
    }

    @Override
    protected void saveOnConditions(Resume resume, int index) {
        listStorage.add(resume);
    }

    @Override
    protected Resume getResume(int index) {
        return listStorage.get(index);
    }

    @Override
    protected void rewriteResume(Resume resume, int index) {
        listStorage.set(index, resume);
    }
}
