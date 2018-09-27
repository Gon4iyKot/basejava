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
                            dos.writeUTF(homePage.getUrl()==null?"null":homePage.getUrl());
                            writeCollection(dos, item.getPositions(), item2 -> {
                                dos.writeUTF(item2.getStartDate().toString());
                                dos.writeUTF(item2.getEndDate().toString());
                                dos.writeUTF(item2.getTitle());
                                dos.writeUTF(item2.getDescription()==null?"null":item2.getDescription());
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
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            dis.readInt(); //костыль №1
            while (dis.available() != 0) {
                String sectionName = dis.readUTF();
                switch (sectionName) {
                    case "PERSONAL":
                    case "OBJECTIVE":
                        String text = dis.readUTF();
                        resume.addSection(SectionType.valueOf(sectionName), new TextSection(text));
                        break;
                    case "ACHIEVEMENT":
                    case "QUALIFICATIONS":
                        resume.addSection(SectionType.valueOf(sectionName), new ListSection(readList(dis, dis::readUTF)));
                        break;
                    case "EXPERIENCE":
                    case "EDUCATION":
                        resume.addSection(SectionType.valueOf(sectionName), new OrganizationSection(readList(dis, () ->
                                new Organization(new Link(dis.readUTF(), nullChecker(dis.readUTF())), readList(dis, () ->
                                        new Organization.Position(LocalDate.parse(dis.readUTF()),
                                                LocalDate.parse(dis.readUTF()), dis.readUTF(), nullChecker(dis.readUTF())))))));
                        break;
                }
            }
            return resume;
        }
    }

    private String nullChecker (String str) {
        return str.equals("null")?null:str;
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
}