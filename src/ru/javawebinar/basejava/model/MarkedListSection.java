package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class MarkedListSection extends AbstractSection {
    private List<String> text;

    public MarkedListSection(String titleOfSection, ArrayList<String> text) {
        this.titleOfSection = titleOfSection;
        this.text = text;
    }

    public List<String> getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MarkedListSection that = (MarkedListSection) o;

        return text.equals(that.text);
    }

    @Override
    public int hashCode() {
        return text.hashCode();
    }

    @Override
    public String toString() {
        return titleOfSection + "\n" + text;
    }
}