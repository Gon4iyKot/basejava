package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            Map<SectionType, Section> sections = resume.getSections();
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                String sectionName = entry.getKey().name();
                dos.writeUTF(sectionName);
                switch (sectionName) {
                    case "PERSONAL":
                    case "OBJECTIVE":
                        dos.writeUTF(((TextSection) entry.getValue()).getTextInfo());
                        break;
                    case "ACHIEVEMENT":
                    case "QUALIFICATIONS":
                        List<String> items = ((ListSection) entry.getValue()).getItems();
                        dos.writeInt(items.size());
                        for (String item : items) {
                            dos.writeUTF(item);
                        }
                        break;
                    case "EXPERIENCE":
                    case "EDUCATION":
                        List<Organization> organizations = ((OrganizationSection) entry.getValue()).getOrganizations();
                        dos.writeInt(organizations.size());
                        for (Organization item : organizations) {
                            List<Organization.Position> positions = item.getPositions();
                            Link homePage = item.getHomePage();
                            dos.writeUTF(homePage.getName());
                            dos.writeUTF(homePage.getUrl());
                            dos.writeInt(positions.size());
                            for (Organization.Position position : positions) {
                                dos.writeUTF(position.getStartDate().toString());
                                dos.writeUTF(position.getEndDate().toString());
                                dos.writeUTF(position.getTitle());
                                dos.writeUTF(position.getDescription());
                            }
                        }
                        break;
                }
            }

        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullname = dis.readUTF();
            Resume resume = new Resume(uuid, fullname);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            while (dis.available() > 0) {
                String sectionName = dis.readUTF();
                switch (sectionName) {
                    case "PERSONAL":
                    case "OBJECTIVE":
                        String text = dis.readUTF();
                        resume.addSection(SectionType.valueOf(sectionName), new TextSection(text));
                        break;
                    case "ACHIEVEMENT":
                    case "QUALIFICATIONS":
                        List<String> items = new ArrayList<>();
                        int listSize = dis.readInt();
                        for (int i = 0; i < listSize; i++) {
                            items.add(dis.readUTF());
                        }
                        resume.addSection(SectionType.valueOf(sectionName), new ListSection(items));
                        break;
                    case "EXPERIENCE":
                    case "EDUCATION":
                        List<Organization> organizations = new ArrayList<>();
                        int organisationsSize = dis.readInt();
                        for (int i = 0; i < organisationsSize; i++) {
                            List<Organization.Position> positions = new ArrayList<>();
                            String name = dis.readUTF();
                            String url = dis.readUTF();
                            int positionSize = dis.readInt();
                            for (int y = 0; y < positionSize; y++) {
                                LocalDate startDate = LocalDate.parse(dis.readUTF());
                                LocalDate endDate = LocalDate.parse(dis.readUTF());
                                String title = dis.readUTF();
                                String description = dis.readUTF();
                                positions.add(new Organization.Position(startDate, endDate, title, description));
                            }
                            organizations.add(new Organization(new Link(name, url), positions));
                        }
                        resume.addSection(SectionType.valueOf(sectionName), new OrganizationSection(organizations));
                        break;
                }
            }
            return resume;
        }
    }
}
