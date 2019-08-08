package ru.javawebinar.basejava.model;

public class OrganizationSection extends Section {
    private String blockHeader;
    private String yearMonth;
    private String textField;

    public OrganizationSection(String titleOfSection, String blockHeader, String yearMonth, String textField) {
        this.titleOfSection = titleOfSection;
        this.blockHeader = blockHeader;
        this.yearMonth = yearMonth;
        this.textField = textField;
    }
}