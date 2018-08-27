package com.urise.webapp;

import com.urise.webapp.model.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

public class TestSingleton {
    private static TestSingleton instance;

    private static TestSingleton getInstance() {
        if (instance == null) {
            instance = new TestSingleton();
        }
        return instance;
    }

    private TestSingleton() {
    }

    public static void main(String[] args) {
        TestSingleton.getInstance().toString();
        Singleton instance = Singleton.valueOf("INSTANCE");
        System.out.println(instance.ordinal());
        for (SectionType type:SectionType.values()) {
            System.out.println(type.getTitle());
        }
        Resume resume1 = new Resume("fullName");
        resume1.contact.put(ContactType.ADDRESS, "some address");
        resume1.contact.put(ContactType.SKYPE, "some skype");
        resume1.contact.put(ContactType.TELEPHONE, "some nomber");
        resume1.contact.put(ContactType.EMAIL, "random e-mail");
        resume1.section.put(SectionType.OBJECTIVE, new TextSection("some objective"));
        resume1.section.put(SectionType.PERSONAL, new TextSection("some personal info"));
        resume1.section.put(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList("1 achievement","2 achievement")));

        resume1.section.put(SectionType.EXPERIENCE, new ContentBlock(Collections.singletonList(
                                                    new ContentUnit("title", new Date(), new Date(),
                                                                    "subtitle", "description"))));

        resume1.section.put(SectionType.EDUCATION, new ContentBlock(Collections.singletonList(
                                                   new ContentUnit("title", new Date(), new Date(),
                                                                   "subtitle", "description"))));


        System.out.println(resume1);
    }

    public enum Singleton{
        INSTANCE
    }
}
