package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

public class InstanceOfResume {
    public static void main(String[] args) {
        Resume resume1 = new Resume("fullName");
        resume1.addContact(ContactType.ADDRESS, "some address");
        resume1.addContact(ContactType.SKYPE, "some skype");
        resume1.addContact(ContactType.TELEPHONE, "some nomber");
        resume1.addContact(ContactType.EMAIL, "random e-mail");
        resume1.addContact(ContactType.GITHUB, "some github");
        resume1.addContact(ContactType.LINKEDIN, "some linkedin");
        resume1.addContact(ContactType.STACKOVERFLOW, "some stackoverflow");
        resume1.addContact(ContactType.PERSONALPAGE, "some page");
        resume1.addSection(SectionType.OBJECTIVE, new TextSection("some objective"));
        resume1.addSection(SectionType.PERSONAL, new TextSection("some personal info"));
        resume1.addSection(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList("1 achievement", "2 achievement")));
        resume1.addSection(SectionType.EXPERIENCE, new OrganizationSection(Collections.singletonList(
                new Organization("title", null,
                        new Organization.Position(LocalDate.now(), LocalDate.now(), "subtitle", "description")))));
        resume1.addSection(SectionType.EDUCATION, new OrganizationSection(Collections.singletonList(
                new Organization("title", null,
                        new Organization.Position(LocalDate.now(), LocalDate.now(), "subtitle", "description")))));
        System.out.println(resume1);
    }
}
