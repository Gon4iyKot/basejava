package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    public void save(Resume resume) {
        if (getIndex(resume.getUuid()) >= 0) {
            System.out.println("Резюме " + resume.getUuid() + " уже существует, попробуйте в другой раз");
        } else if (size >= STORAGE_LIMIT) {
            System.out.println("Слишком много резюме");
        } else if (size == 0) {
            storage[0] = resume;
            size++;
        } else {
            int insertIndex = binaryInsert(resume);
            System.out.println(binaryInsert(resume));
            System.arraycopy(storage, insertIndex, storage, insertIndex + 1, size - insertIndex);
            storage[insertIndex] = resume;
            size++;
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("Резюме не существует, попробуйте в другой раз");
            return null;
        }
        return storage[index];
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            System.out.println("Резюме не существует, попробуйте в другой раз");
        } else {
            storage[index] = resume;
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("Резюме не существует, попробуйте в другой раз");
        } else {
            System.arraycopy(storage, index + 1, storage, index, size - index - 1);
            storage[size - 1] = null;
            size--;
        }
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    private int binaryInsert(Resume resume) {
        int min = 0;
        int max = size - 1;

        while (min <= max) {
            int mid = (min + max) >>> 1;
            int compareVar = storage[mid].compareTo(resume);
            if (size == 0) {
                return 0;
            }
            if ((min == mid) && (compareVar > 0)) {
                return mid;
            }
            if (compareVar < 0) {
                min = mid + 1;
                if (min > max) {
                    return min;
                }
            } else {
                max = mid - 1;
            }
        }
        return 1;
    }

}
