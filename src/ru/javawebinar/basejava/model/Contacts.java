package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

class Contacts {
    private List<String> contacts = new ArrayList<>();

    void addContact(String string) {
        contacts.add(string);
    }

    void printContacts() {
        for (String contact : contacts) {
            System.out.println(contact);
        }
    }
}