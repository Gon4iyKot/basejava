package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;


/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    final public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            System.out.println("Резюме " + resume.getUuid() + " уже существует, попробуйте в другой раз");
        } else if (size >= STORAGE_LIMIT) {
            System.out.println("Слишком много резюме");
        } else {
            insertResume(resume, index);
            size++;
        }
    }

    final public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("Резюме не существует, попробуйте в другой раз");
            return null;
        }
        return storage[index];
    }

    final public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            System.out.println("Резюме не существует, попробуйте в другой раз");
        } else {
            storage[index] = resume;
        }
    }

    final public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("Резюме не существует, попробуйте в другой раз");
        } else {
            deleteResume(index);
            storage[size - 1] = null;
            size--;
        }
    }

    final public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    final public int size() {
        return size;
    }

    final public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    abstract protected int getIndex(String uuid);

    abstract protected void insertResume(Resume resume, int index);

    abstract protected void deleteResume(int index);

}