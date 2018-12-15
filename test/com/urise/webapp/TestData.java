package com.urise.webapp;

import com.urise.webapp.model.*;
import com.urise.webapp.util.DateUtil;

import java.time.Month;
import java.util.Arrays;
import java.util.Collections;

public class TestData {
    public static final String UUID_1 = "uuid1";
    public static final String UUID_2 = "uuid2";
    public static final String UUID_3 = "uuid3";
    public static final String UUID_4 = "uuid4";

    public static final Resume R1 = new Resume(UUID_1, "fullName1");
    public static final Resume R2 = new Resume(UUID_2, "fullName2");
    public static final Resume R3 = new Resume(UUID_3, "fullName3");
    public static final Resume R4 = new Resume(UUID_4, "fullName4");

    static {
        R1.addContact(ContactType.ADDRESS, "some address");
        R1.addContact(ContactType.SKYPE, "some skype");
        R1.addContact(ContactType.TELEPHONE, "some nomber");
        R1.addContact(ContactType.EMAIL, "random e-mail");
        R1.addContact(ContactType.GITHUB, "some github");
        R1.addContact(ContactType.LINKEDIN, "some linkedin");
        R1.addContact(ContactType.STACKOVERFLOW, "some stackoverflow");
        R1.addContact(ContactType.PERSONALPAGE, "some page");
        R1.addSection(SectionType.OBJECTIVE, new TextSection("some objective"));
        R1.addSection(SectionType.PERSONAL, new TextSection("some personal info"));
        R1.addSection(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList("some achievement", "other achievement")));
        R1.addSection(SectionType.QUALIFICATIONS, new ListSection(Arrays.asList("some qualification", "one more qualification")));
                R1.addSection(SectionType.EXPERIENCE, new OrganizationSection(Collections.singletonList(
                new Organization("111111", "https://www.google.com",
                        new Organization.Position(DateUtil.of(2016, Month.DECEMBER), DateUtil.of(2018, Month.JULY), "subtitle", "вфафы")))));
        R1.addSection(SectionType.EDUCATION, new OrganizationSection(Collections.singletonList(
                new Organization("111111", "https://www.google.com",
                        new Organization.Position(DateUtil.of(2016, Month.DECEMBER), DateUtil.of(2018, Month.JULY), "subtitle", "description")))));
        R2.addContact(ContactType.ADDRESS, "some address 2");
        R2.addContact(ContactType.SKYPE, "some skype 2");
        R2.addContact(ContactType.TELEPHONE, "some nomber 2");
        R2.addContact(ContactType.EMAIL, "random e-mail 2");
        R2.addContact(ContactType.GITHUB, "some github 2");
        R2.addContact(ContactType.LINKEDIN, "some linkedin 2");
        R2.addContact(ContactType.STACKOVERFLOW, "some stackoverflow 2");
        R2.addContact(ContactType.PERSONALPAGE, "some page 2");
        R2.addSection(SectionType.OBJECTIVE, new TextSection("some objective 2"));
        R2.addSection(SectionType.PERSONAL, new TextSection("some personal info 2"));
        R2.addSection(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList("1 achievement", "2 achievement 2")));
        R2.addSection(SectionType.QUALIFICATIONS, new ListSection(Arrays.asList("1 qualification", "2 qualification")));
        R2.addSection(SectionType.EXPERIENCE, new OrganizationSection(Collections.singletonList(
                new Organization("111111", "https://www.google.com",
                        new Organization.Position(DateUtil.of(2016, Month.DECEMBER), DateUtil.of(2018, Month.JULY), "subtitle", "description")))));
        R2.addSection(SectionType.EDUCATION, new OrganizationSection(Collections.singletonList(
                new Organization("111111", "https://www.google.com",
                        new Organization.Position(DateUtil.of(2016, Month.DECEMBER), DateUtil.of(2018, Month.JULY), "subtitle", "description")))));
        R3.addContact(ContactType.ADDRESS, "some address 3");
        R3.addContact(ContactType.SKYPE, "some skype 3");
        R3.addContact(ContactType.TELEPHONE, "some nomber 3");
        R3.addContact(ContactType.EMAIL, "random e-mail 3");
        R3.addContact(ContactType.GITHUB, "some github 3");
        R3.addContact(ContactType.LINKEDIN, "some linkedin 3");
        R3.addContact(ContactType.STACKOVERFLOW, "some stackoverflow 3");
        R3.addContact(ContactType.PERSONALPAGE, "some page 3");
        R3.addSection(SectionType.OBJECTIVE, new TextSection("some objective 3"));
        R3.addSection(SectionType.PERSONAL, new TextSection("some personal info 3"));
        R3.addSection(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList("1 achievement", "2 achievement 3")));
        R3.addSection(SectionType.EXPERIENCE, new OrganizationSection(Collections.singletonList(
                new Organization("111111", "https://www.google.com",
                        new Organization.Position(DateUtil.of(2016, Month.DECEMBER), DateUtil.of(2018, Month.JULY), "subtitle", "description")))));
        R3.addSection(SectionType.EDUCATION, new OrganizationSection(Collections.singletonList(
                new Organization("111111", null,
                        new Organization.Position(DateUtil.of(2016, Month.DECEMBER), DateUtil.of(2018, Month.JULY), "subtitle", null)))));
    }
}