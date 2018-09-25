package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.Month;
import java.util.Arrays;
import java.util.Collections;

public class InstanceOfResume {
    public static void main(String[] args) {
        Resume resume1 = new Resume("uuid404","fullName");
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
                new Organization("title", "dsfsdfsdf",
                        new Organization.Position(2005, Month.AUGUST, 2016, Month.DECEMBER, "subtitle", "description")))));
        resume1.addSection(SectionType.EDUCATION, new OrganizationSection(Collections.singletonList(
                new Organization("title", "dsfsdfsdf",
                        new Organization.Position(2017, Month.APRIL, "subtitle", "description")))));
        System.out.println(resume1);
/*        List date1 = ((OrganizationSection)resume1.getSection(SectionType.EDUCATION)).getOrganizations().get(0).getPositions();
        String date11 = ((Organization.Position)date1.get(0)).getEndDate().toString();
        System.out.println(date11);
        LocalDate ld = LocalDate.parse(date11);
        System.out.println(ld);
        File STORAGE_DIR = new File("C:\\Users\\Admin\\Documents\\GitHub\\basejava\\storage");
        Storage storage = new PathStorage(STORAGE_DIR.getAbsolutePath(), new DataStreamSerializer());
        storage.delete("uuid404");
        storage.save(resume1);*/
    }
}
