package ru.javawebinar.basejava.model;

public class Contact {
    private String contact;

    public Contact(String contact) {
        this.contact = contact;
    }

    public String getContact() {
        return contact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        return this.contact.equals(contact.contact);
    }

    @Override
    public int hashCode() {
        return contact.hashCode();
    }

    @Override
    public String toString() {
        return contact;
    }
}