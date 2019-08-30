package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ru.javawebinar.basejava.model.SectionType.*;

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
                if (sectionType.equals(OBJECTIVE) || sectionType.equals(PERSONAL)) {
                    dos.writeUTF(((TextSection) section).getTitle());
                    dos.writeUTF(((TextSection) section).getText());
                }
                if (sectionType.equals(ACHIEVEMENT) || sectionType.equals(QUALIFICATIONS)) {
                    dos.writeUTF(((ListSection) section).getTitle());
                    List<String> list = ((ListSection) section).getText();
                    dos.writeInt(list.size());
                    for (String string : list) {
                        dos.writeUTF(string);
                    }
                }
                if (sectionType.equals(EXPERIENCE) || sectionType.equals(EDUCATION)) {
                    dos.writeUTF(((OrganizationSection) section).getTitle());
                    List<Organization> list = ((OrganizationSection) section).getOrganizations();
                    dos.writeInt(list.size());
                    for (Organization organization : list) {
                        Link link = organization.getLink();
                        dos.writeUTF(link.getName());
                        dos.writeUTF(link.getUrl());
                        List<Organization.OrganizationPeriod> organizationPeriods = organization.getPeriods();
                        dos.writeInt(organizationPeriods.size());
                        for (Organization.OrganizationPeriod organizationPeriod : organizationPeriods) {
                            YearMonth startDate = organizationPeriod.getStartDate();
                            dos.writeInt(startDate.getYear());
                            dos.writeInt(startDate.getMonthValue());
                            YearMonth endDate = organizationPeriod.getEndDate();
                            dos.writeInt(endDate.getYear());
                            dos.writeInt(endDate.getMonthValue());
                            dos.writeUTF(organizationPeriod.getTitle());
                            dos.writeUTF(organizationPeriod.getDescription());
                        }
                    }
                }
            }
        }
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
        if (sectionType.equals(OBJECTIVE) || sectionType.equals(PERSONAL)) {
            return new TextSection(dis.readUTF(), dis.readUTF());
        }
        if (sectionType.equals(ACHIEVEMENT) || sectionType.equals(QUALIFICATIONS)) {
            String title = dis.readUTF();
            int size = dis.readInt();
            ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                list.add(dis.readUTF());
            }
            return new ListSection(title, list);
        } else {
            String title = dis.readUTF();
            int size = dis.readInt();
            ArrayList<Organization> organizations = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                String name = dis.readUTF();
                String url = dis.readUTF();
                int numberOfPeriods = dis.readInt();
                ArrayList<Organization.OrganizationPeriod> organizationPeriods = new ArrayList<>(numberOfPeriods);
                for (int n = 0; n < numberOfPeriods; n++) {
                    YearMonth startDate = YearMonth.of(dis.readInt(), dis.readInt());
                    YearMonth endDate = YearMonth.of(dis.readInt(), dis.readInt());
                    organizationPeriods.add(new Organization.OrganizationPeriod(startDate, endDate, dis.readUTF(), dis.readUTF()));
                }
                organizations.add(new Organization(name, url, organizationPeriods));
            }
            return new OrganizationSection(title, organizations);
        }
    }
}