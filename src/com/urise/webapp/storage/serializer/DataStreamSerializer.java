package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            writeCollection(dos, resume.getContacts().entrySet(), item -> {
                dos.writeUTF(item.getKey().name());
                dos.writeUTF(item.getValue());
            });
            writeCollection(dos, resume.getSections().entrySet(), entry ->
            {
                String sectionName = entry.getKey().name();
                dos.writeUTF(sectionName);
                Section value = entry.getValue();
                switch (sectionName) {
                    case "PERSONAL":
                    case "OBJECTIVE":
                        dos.writeUTF(((TextSection) value).getTextInfo());
                        break;
                    case "ACHIEVEMENT":
                    case "QUALIFICATIONS":
                        List<String> items = ((ListSection) value).getItems();
                        writeCollection(dos, items, dos::writeUTF);
                        break;
                    case "EXPERIENCE":
                    case "EDUCATION":
                        List<Organization> organizations = ((OrganizationSection) value).getOrganizations();
                        writeCollection(dos, organizations, item -> {
                            Link homePage = item.getHomePage();
                            dos.writeUTF(homePage.getName());
                            dos.writeUTF(homePage.getUrl() == null ? "" : homePage.getUrl());
                            writeCollection(dos, item.getPositions(), item2 -> {
                                dos.writeUTF(item2.getStartDate().toString());
                                dos.writeUTF(item2.getEndDate().toString());
                                dos.writeUTF(item2.getTitle());
                                dos.writeUTF(item2.getDescription() == null ? "" : item2.getDescription());
                            });

                        });
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
            readCollection(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readCollection(dis, () -> {
                SectionType sectionName = SectionType.valueOf(dis.readUTF());
                switch (sectionName) {
                    case PERSONAL:
                    case OBJECTIVE:
                        String text = dis.readUTF();
                        resume.addSection(sectionName, new TextSection(text));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        resume.addSection(sectionName, new ListSection(readList(dis, dis::readUTF)));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        SectionType type = sectionName;
                        OrganizationSection organizationSection = new OrganizationSection(readList(dis, () -> {
                            Link homePage = new Link(dis.readUTF(), nullChecker(dis.readUTF()));
                            List<Organization.Position> positions = readList(dis, () -> {
                                LocalDate startDate = LocalDate.parse(dis.readUTF());
                                LocalDate endDate = LocalDate.parse(dis.readUTF());
                                String title = dis.readUTF();
                                String description = nullChecker(dis.readUTF());
                                return new Organization.Position(startDate, endDate, title, description);
                            });
                            return new Organization(homePage, positions);
                        }));
                        resume.addSection(type, organizationSection);
                        break;
                }
            });
            return resume;
        }
    }

    private String nullChecker(String str) {
        return str.equals("") ? null : str;
    }

    @FunctionalInterface
    private interface WriteInterface<T> {
        void write(T obj) throws IOException;
    }

    private <T> void writeCollection(DataOutputStream dos, Collection<T> collection, WriteInterface<T> writeInterface) throws IOException {
        dos.writeInt(collection.size());
        for (T item : collection) {
            writeInterface.write(item);
        }
    }

    @FunctionalInterface
    private interface ReadInterface<T> {
        T read() throws IOException;
    }

    private <T> List<T> readList(DataInputStream dis, ReadInterface<T> readInterface) throws IOException {
        List<T> items = new ArrayList<>();
        int listSize = dis.readInt();
        for (int i = 0; i < listSize; i++) {
            items.add(readInterface.read());
        }
        return items;
    }

    @FunctionalInterface
    private interface ActionInterface {
        void doAction() throws IOException;
    }

    private void readCollection(DataInputStream dis, ActionInterface actionInterface) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            actionInterface.doAction();
        }
    }
}