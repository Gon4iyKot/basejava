package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;


/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int counter = 0;

    public void clear() {
        if (counter != 0) {
            Arrays.fill(storage, 0, (counter - 1), null);
            counter = 0;
        }
    }

    public void save(Resume resume) {
        if (checkIndex(resume.getUuid()) == null) {
            storage[counter] = resume;
            counter++;
        } else {
            System.out.println("Резюме уже существует, попробуйте в другой раз");
        }
    }

    public void update(String uuid, String uuid2) {
        Integer indexChecker = checkIndex(uuid);
        if (indexChecker != null) {
            storage[indexChecker] = new Resume(uuid2);
        } else {
            System.out.println("Резюме не существует, попробуйте в другой раз");
        }

    }

    public Resume get(String uuid) {
        Integer indexChecker = checkIndex(uuid);
        if (indexChecker != null) {
            return storage[indexChecker];
        } else {
            System.out.println("Резюме не существует, попробуйте в другой раз");
            return null;
        }
    }

    public void delete(String uuid) {
        Integer indexChecker = checkIndex(uuid);
        if (indexChecker != null) {
            storage[indexChecker] = storage[counter - 1];
            storage[counter - 1] = null;
            counter--;
        } else {
            System.out.println("Резюме не существует, попробуйте в другой раз");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, counter);
    }

    public int size() {
        return counter;
    }

    private Integer checkIndex(String uuid) {
        for (int i = 0; i < counter; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

}
