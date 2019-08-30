package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private List<String> text;

    public ListSection() {
    }

    public ListSection(String titleOfSection, String... text) {
        this(titleOfSection, new ArrayList<>(Arrays.asList(text)));
    }

    public ListSection(String titleOfSection, ArrayList<String> text) {
        Objects.requireNonNull(text, "text must not be null");
        this.titleOfSection = titleOfSection;
        this.text = text;
    }

    public String getTitle() {
        return titleOfSection;
    }

    public List<String> getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection that = (ListSection) o;

        return text.equals(that.text);
    }

    @Override
    public int hashCode() {
        return text.hashCode();
    }

    @Override
    public String toString() {
        return "\n\n" + titleOfSection + "\n" + text;
    }
}