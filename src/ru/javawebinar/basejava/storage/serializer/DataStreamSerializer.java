package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements IOStrategy {

    @Override
    public void doWrite(OutputStream os, Resume r) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());

            Map<ContactType, Contact> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, Contact> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue().getContact());
            }

            Map<SectionType, AbstractSection> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                SectionType sectionType = entry.getKey();
                dos.writeUTF(sectionType.name());

                AbstractSection section = entry.getValue();
                switch (sectionType) {
                    case OBJECTIVE:
                    case PERSONAL:
                        dos.writeUTF(((TextSection) section).getText());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> list = ((ListSection) section).getText();
                        dos.writeInt(list.size());
                        for (String string : list) {
                            dos.writeUTF(string);
                        }
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> organizations = ((OrganizationSection) section).getOrganizations();
                        dos.writeInt(organizations.size());
                        for (Organization organization : organizations) {
                            Link link = organization.getLink();
                            dos.writeUTF(link.getName());
                            if (link.getUrl() != null) {
                                dos.writeInt(1);
                                dos.writeUTF(link.getUrl());
                            } else {
                                dos.writeInt(0);
                            }
                            List<Organization.OrganizationPeriod> organizationPeriods = organization.getPeriods();
                            dos.writeInt(organizationPeriods.size());
                            for (Organization.OrganizationPeriod organizationPeriod : organizationPeriods) {
                                writeDate(dos, organizationPeriod.getStartDate());
                                writeDate(dos, organizationPeriod.getEndDate());
                                dos.writeUTF(organizationPeriod.getTitle());
                                if (organizationPeriod.getDescription() != null) {
                                    dos.writeInt(1);
                                    dos.writeUTF(organizationPeriod.getDescription());
                                } else {
                                    dos.writeInt(0);
                                }
                            }
                        }
                        break;
                }
            }
        }
    }

    private void writeDate(DataOutputStream dos, YearMonth date) throws IOException {
        dos.writeInt(date.getYear());
        dos.writeInt(date.getMonthValue());
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), new Contact(dis.readUTF()));
            }

            int numberOfSections = dis.readInt();
            for (int i = 0; i < numberOfSections; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                resume.addSection(sectionType, readSection(dis, sectionType));
            }
            return resume;
        }
    }

    private AbstractSection readSection(DataInputStream dis, SectionType sectionType) throws IOException {
        switch (sectionType) {
            case OBJECTIVE:
            case PERSONAL:
                return new TextSection(dis.readUTF());
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                int size = dis.readInt();
                ArrayList<String> list = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    list.add(dis.readUTF());
                }
                return new ListSection(list);
            case EXPERIENCE:
            case EDUCATION:
                int numberOfOrganizations = dis.readInt();
                ArrayList<Organization> organizations = new ArrayList<>(numberOfOrganizations);
                for (int i = 0; i < numberOfOrganizations; i++) {
                    String name = dis.readUTF();
                    String url;
                    if (dis.readInt() == 1) {
                        url = dis.readUTF();
                    } else {
                        url = null;
                    }
                    int numberOfPeriods = dis.readInt();
                    ArrayList<Organization.OrganizationPeriod> organizationPeriods = new ArrayList<>(numberOfPeriods);
                    for (int n = 0; n < numberOfPeriods; n++) {
                        YearMonth startDate = readDate(dis);
                        YearMonth endDate = readDate(dis);
                        String title = dis.readUTF();
                        String description;
                        if (dis.readInt() == 1) {
                            description = dis.readUTF();
                        } else {
                            description = null;
                        }
                        organizationPeriods.add(new Organization.OrganizationPeriod(startDate, endDate, title, description));
                    }
                    organizations.add(new Organization(name, url, organizationPeriods));
                }
                return new OrganizationSection(organizations);
            default:
                throw new IllegalArgumentException("Section Type Error!");
        }
    }

    private YearMonth readDate(DataInputStream dis) throws IOException {
        return YearMonth.of(dis.readInt(), dis.readInt());
    }
}