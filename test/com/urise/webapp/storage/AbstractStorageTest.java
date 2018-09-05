package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {

    protected Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final Resume resume1 = new Resume(UUID_1, "fullName1");
    private static final Resume resume2 = new Resume(UUID_2, "fullName2");
    private static final Resume resume3 = new Resume(UUID_3, "fullName3");
    private static final Resume resume4 = new Resume(UUID_4, "fullName4");

    static {
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
        resume1.section.put(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList("1 achievement", "2 achievement")));
        resume1.section.put(SectionType.EXPERIENCE, new OrganisationSection(Collections.singletonList(
                new Organization("title", null, LocalDate.now(), LocalDate.now(), "subtitle", "description"))));
        resume1.section.put(SectionType.EDUCATION, new OrganisationSection(Collections.singletonList(
                new Organization("title", null, LocalDate.now(), LocalDate.now(), "subtitle", "description"))));
        resume2.contact.put(ContactType.ADDRESS, "some address 2");
        resume2.contact.put(ContactType.SKYPE, "some skype 2");
        resume2.contact.put(ContactType.TELEPHONE, "some nomber 2");
        resume2.contact.put(ContactType.EMAIL, "random e-mail 2");
        resume2.contact.put(ContactType.GITHUB, "some github 2");
        resume2.contact.put(ContactType.LINKEDIN, "some linkedin 2");
        resume2.contact.put(ContactType.STACKOVERFLOW, "some stackoverflow 2");
        resume2.contact.put(ContactType.PERSONALPAGE, "some page 2");
        resume2.section.put(SectionType.OBJECTIVE, new TextSection("some objective 2"));
        resume2.section.put(SectionType.PERSONAL, new TextSection("some personal info 2"));
        resume2.section.put(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList("1 achievement", "2 achievement 2")));
        resume2.section.put(SectionType.EXPERIENCE, new OrganisationSection(Collections.singletonList(
                new Organization("title", null, LocalDate.now(), LocalDate.now(), "subtitle", "description"))));
        resume2.section.put(SectionType.EDUCATION, new OrganisationSection(Collections.singletonList(
                new Organization("title", null, LocalDate.now(), LocalDate.now(), "subtitle", "description"))));
        resume3.contact.put(ContactType.ADDRESS, "some address 3");
        resume3.contact.put(ContactType.SKYPE, "some skype 3");
        resume3.contact.put(ContactType.TELEPHONE, "some nomber 3");
        resume3.contact.put(ContactType.EMAIL, "random e-mail 3");
        resume3.contact.put(ContactType.GITHUB, "some github 3");
        resume3.contact.put(ContactType.LINKEDIN, "some linkedin 3");
        resume3.contact.put(ContactType.STACKOVERFLOW, "some stackoverflow 3");
        resume3.contact.put(ContactType.PERSONALPAGE, "some page 3");
        resume3.section.put(SectionType.OBJECTIVE, new TextSection("some objective 3"));
        resume3.section.put(SectionType.PERSONAL, new TextSection("some personal info 3"));
        resume3.section.put(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList("1 achievement", "2 achievement 3")));
        resume3.section.put(SectionType.EXPERIENCE, new OrganisationSection(Collections.singletonList(
                new Organization("title", null, LocalDate.now(), LocalDate.now(), "subtitle", "description"))));
        resume3.section.put(SectionType.EDUCATION, new OrganisationSection(Collections.singletonList(
                new Organization("title", null, LocalDate.now(), LocalDate.now(), "subtitle", "description"))));
    }

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
    }

    @Test
    public void save() {
        storage.save(resume4);
        assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(resume1);
    }

    @Test
    public void update() {
        Resume resume = new Resume(UUID_1, "anyOtherFullName");
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
    public void getAll() {
        List<Resume> tempStorage = Arrays.asList(resume1, resume2, resume3);
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
        assertEquals(resume1, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID_4);
    }
}