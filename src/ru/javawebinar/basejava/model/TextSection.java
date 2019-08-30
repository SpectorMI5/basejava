package ru.javawebinar.basejava.model;

import java.util.Objects;

public class TextSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private String text;

    public TextSection() {
    }

    public TextSection(String titleOfSection, String text) {
        Objects.requireNonNull(text, "text must not be null");
        this.titleOfSection = titleOfSection;
        this.text = text;
    }

    public String getTitle() {
        return titleOfSection;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextSection that = (TextSection) o;

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