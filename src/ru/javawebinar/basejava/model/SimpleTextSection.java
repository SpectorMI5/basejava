package ru.javawebinar.basejava.model;

public class SimpleTextSection extends Section {
    private String textField;

    public SimpleTextSection(String titleOfSection, String textField) {
        this.titleOfSection = titleOfSection;
        this.textField = textField;
    }

    @Override
    public String toString() {
        return titleOfSection + " | " + textField;
    }
}