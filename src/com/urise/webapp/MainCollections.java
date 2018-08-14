package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MainCollections {
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String FULL_NAME_1 = "fullName1";
    private static final String FULL_NAME_2 = "fullName2";
    private static final String FULL_NAME_3 = "fullName3";
    private static final String FULL_NAME_4 = "fullName4";

    private static final Resume resume1 = new Resume(UUID_1, FULL_NAME_1);
    private static final Resume resume2 = new Resume(UUID_2, FULL_NAME_2);
    private static final Resume resume3 = new Resume(UUID_3, FULL_NAME_3);
    private static final Resume resume4 = new Resume(UUID_4, FULL_NAME_4);

    public static void main(String[] args) {
        Collection<Resume> collection = new ArrayList<>();
        collection.add(resume1);
        collection.add(resume2);
        collection.add(resume3);
        for (Resume r : collection) {
            System.out.println(r);
            if (Objects.equals(r.getUuid(), UUID_1)) {
                //collection.remove(r);
            }
        }
        Iterator<Resume> iterator = collection.iterator();
        while (iterator.hasNext()) {
            Resume resume = iterator.next();
            System.out.println(resume);
            if (Objects.equals(resume.getUuid(), UUID_1)) {
                iterator.remove();
            }

        }
        System.out.println(collection.toString());
        Map<String, Resume> map = new HashMap();
        map.put(UUID_1, resume1);
        map.put(UUID_2, resume2);
        map.put(UUID_3, resume3);

        for (String uuid : map.keySet()) {
            System.out.println(map.get(uuid));
        }
        for (Map.Entry<String, Resume> entry : map.entrySet()) {
            System.out.println(entry.getValue());
        }

    }
}
