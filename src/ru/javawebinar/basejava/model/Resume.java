package ru.javawebinar.basejava.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Resume {

    // Unique identifier
    private final String uuid;

    private final String fullName;

    private final Contacts fields = new Contacts();

    private final Map<String, String> contacts = new HashMap<>();

    private final Map<SectionType, Section> sections = new HashMap<>();

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void addContact(String contact) {
        String[] splitContact = contact.split(" ");
        String contactType = splitContact[0];
        contacts.put(contactType, contact);
        fields.addContact(contact);
    }

    public String getContact(String contactType) {
        return contacts.get(contactType);
    }

    public void printResume() {
        fields.printContacts();
        System.out.println(sections.toString());
    }

    public void addSection(SectionType sectionType, Section section) {
        sections.put(sectionType, section);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (!uuid.equals(resume.uuid)) return false;
        return fullName.equals(resume.fullName);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return uuid + " " + fullName;
    }
}