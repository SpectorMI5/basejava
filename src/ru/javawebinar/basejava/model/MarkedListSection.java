package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class MarkedListSection extends Section {
    private List<String> textFields = new ArrayList<>();

    public MarkedListSection(String titleOfSection, ArrayList<String> textFields) {
        this.titleOfSection = titleOfSection;
        this.textFields = textFields;
    }

    @Override
    public String toString() {
        return titleOfSection + " | " + textFields;
    }
}