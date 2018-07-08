package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    public void save(Resume resume) {
        if (getIndex(resume.getUuid()) != -1) {
            System.out.println("Резюме уже существует, попробуйте в другой раз");
        } else if (size >= STORAGE_LIMIT) {
            System.out.println("Слишком много резюме");
        } else {
            int insertIndex = binaryInsert(resume);
            System.arraycopy(storage, insertIndex, storage, insertIndex+1, size-insertIndex-1);
            storage[insertIndex] = resume;
            size++;
        }
    }
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("Резюме не существует, попробуйте в другой раз");
        } else {
            System.arraycopy(storage, index, storage, index+1, size-index-1);
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
        if (storage.length==0) {
            return 0;
        }
        int min = 0;
        int max = storage.length - 1;

        while (min<=max) {
            int mid = (min + max) >>> 1;
            int compareVar = storage[mid].compareTo(resume);
            if ((min==mid)&&(compareVar==1)) {
                return mid;
            } else if (compareVar == -1) {
                min = mid+1;
            } else {
                max = mid -1;
            }
        }
        return 1;
    }

}
