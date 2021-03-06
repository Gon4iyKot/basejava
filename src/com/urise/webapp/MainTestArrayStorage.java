package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.ListStorage;

/**
 * Test for com.urise.webapp.storage.com.urise.webapp.storage.ArrayStorage
 */
public class MainTestArrayStorage {
    private static final ListStorage ARRAY_STORAGE = new ListStorage();

    public static void main(String[] args) {
        final Resume r1 = new Resume("uuid1", "fullName1");
        final Resume r2 = new Resume("uuid2", "fullName2");
        final Resume r3 = new Resume("uuid3", "fullName3");
        final Resume r4 = new Resume("uuid4", "fullName4");
        final Resume r5 = new Resume("uuid5", "fullName5");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r4);
        ARRAY_STORAGE.save(r3);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r5);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());
        //System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        printAll();
        System.out.print("удаление r3");
        ARRAY_STORAGE.delete(r3.getUuid());
        printAll();
        System.out.print("апдейт r2");
        ARRAY_STORAGE.update(r2);
        printAll();
        System.out.print("очистка");
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    private static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAllSorted()) {
            System.out.println(r);
        }
    }
}
