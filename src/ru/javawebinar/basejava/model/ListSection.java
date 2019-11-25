package ru.javawebinar.basejava.model;

import java.util.*;

public class ListSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private List<String> text;

    public ListSection() {
    }

    public ListSection(String... text) {
        this(new ArrayList<>(Arrays.asList(text)));
    }

    public ListSection(List<String> text) {
        Objects.requireNonNull(text, "text must not be null");
        this.text = text;
    }

    public List<String> getText() {
        return text;
    }

    public List<String> getTextWithoutSlash() {
        List<String> list = new LinkedList<>();
        for (String string : text) {
            list.add(string.replace("\"", "'"));
        }
        return list;
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
        return "\n\n" + text;
    }
}