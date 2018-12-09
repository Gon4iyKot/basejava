package com.urise.webapp.storage;

import com.urise.webapp.Config;
import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
    protected final static File STORAGE_DIR = Config.get().getStorageDir();
    protected Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final Resume R1 = new Resume(UUID_1, "fullName1");
    private static final Resume R2 = new Resume(UUID_2, "fullName2");
    private static final Resume R3 = new Resume(UUID_3, "fullName3");
    private static final Resume R4 = new Resume(UUID_4, "fullName4");

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
        /*        R1.addSection(SectionType.EXPERIENCE, new OrganizationSection(Collections.singletonList(
                new Organization("title", "https://www.google.com",
                        new Organization.Position(LocalDate.now(), LocalDate.now(), "subtitle", "вфафы")))));
        R1.addSection(SectionType.EDUCATION, new OrganizationSection(Collections.singletonList(
                new Organization("title", "https://www.google.com",
                        new Organization.Position(LocalDate.now(), LocalDate.now(), "subtitle", "description")))));*/
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
/*
        R2.addSection(SectionType.EXPERIENCE, new OrganizationSection(Collections.singletonList(
                new Organization("title", "https://www.google.com",
                        new Organization.Position(LocalDate.now(), LocalDate.now(), "subtitle", "description")))));
        R2.addSection(SectionType.EDUCATION, new OrganizationSection(Collections.singletonList(
                new Organization("title", "https://www.google.com",
                        new Organization.Position(LocalDate.now(), LocalDate.now(), "subtitle", "description")))));
*/
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
/*
        R3.addSection(SectionType.EXPERIENCE, new OrganizationSection(Collections.singletonList(
                new Organization("title", "https://www.google.com",
                        new Organization.Position(LocalDate.now(), LocalDate.now(), "subtitle", "description")))));
        R3.addSection(SectionType.EDUCATION, new OrganizationSection(Collections.singletonList(
                new Organization("title", null,
                        new Organization.Position(LocalDate.now(), LocalDate.now(), "subtitle", null)))));
*/
    }

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(R1);
        storage.save(R2);
        storage.save(R3);
    }

    @Test
    public void save() {
        storage.save(R4);
        assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(R1);
    }

    @Test
    public void update() {
        Resume resume = new Resume(UUID_1, "anyOtherFullName");
        resume.addContact(ContactType.ADDRESS, "some address");
        resume.addContact(ContactType.SKYPE, "some skype");
        resume.addSection(SectionType.OBJECTIVE, new TextSection("some objective 2"));
        resume.addSection(SectionType.PERSONAL, new TextSection("some personal info 2"));
        resume.addSection(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList("1 achievement", "2 achievement 2")));
        resume.addSection(SectionType.QUALIFICATIONS, new ListSection(Arrays.asList("1 qualification", "2 qualification")));
        storage.update(resume);
        assertEquals(resume, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        Resume resume = new Resume(UUID_4, "anyOtherFullName");
        storage.update(resume);
    }

    @Test
    public void delete() {
        storage.delete(UUID_1);
        assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_4);
    }

    @Test
    public void getAllSorted() {
        List<Resume> tempStorage = Arrays.asList(R1, R2, R3);
        assertEquals(tempStorage, storage.getAllSorted());
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void get() {
        assertEquals(R1, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID_4);
    }
}