package ru.javawebinar.basejava.model;

public class SimpleTextSection extends AbstractSection {
    private String text;

    public SimpleTextSection(String titleOfSection, String text) {
        this.titleOfSection = titleOfSection;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleTextSection that = (SimpleTextSection) o;

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