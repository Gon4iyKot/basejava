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
            Arrays.fill(storage, 0, counter, null);
            counter = 0;
    }

    public void save(Resume resume) {
        if (getIndex(resume.getUuid()) == -1) {
            if (counter!=storage.length) {
                storage[counter] = resume;
                counter++;
            } else {
                System.out.println("Слишком много резюме");
            }
        } else {
            System.out.println("Резюме уже существует, попробуйте в другой раз");
        }
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index != -1) {
            storage[index] = resume;
        } else {
            System.out.println("Резюме не существует, попробуйте в другой раз");
        }

    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index != -1) {
            return storage[index];
        }
            System.out.println("Резюме не существует, попробуйте в другой раз");
            return null;
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index != -1) {
            storage[index] = storage[counter - 1];
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

    private int getIndex(String uuid) {
        for (int i = 0; i < counter; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

}
