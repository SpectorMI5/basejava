package ru.javawebinar.basejava.model;

import java.time.YearMonth;

public class Organization {
    private String organizationName;
    private YearMonth startDate;
    private YearMonth endDate;
    private String text;

    public Organization(String organizationName, YearMonth startDate, YearMonth endDate, String text) {
        this.organizationName = organizationName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!organizationName.equals(that.organizationName)) return false;
        if (!startDate.equals(that.startDate)) return false;
        if (!endDate.equals(that.endDate)) return false;
        return text.equals(that.text);
    }

    @Override
    public int hashCode() {
        int result = organizationName.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + text.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "\n" + organizationName + "\n" + startDate + " - " + endDate + "\n" + text;
    }
}