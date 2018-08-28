package com.urise.webapp;

import com.urise.webapp.model.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

public class InstanceOfResume {
    public static void main(String[] args) {
        Resume resume1 = new Resume("fullName");
        resume1.contact.put(ContactType.ADDRESS, "some address");
        resume1.contact.put(ContactType.SKYPE, "some skype");
        resume1.contact.put(ContactType.TELEPHONE, "some nomber");
        resume1.contact.put(ContactType.EMAIL, "random e-mail");
        resume1.contact.put(ContactType.GITHUB, "some github");
        resume1.contact.put(ContactType.LINKEDIN, "some linkedin");
        resume1.contact.put(ContactType.STACKOVERFLOW, "some stackoverflow");
        resume1.contact.put(ContactType.PERSONALPAGE, "some page");
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
}
