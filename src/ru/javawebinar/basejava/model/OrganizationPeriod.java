package ru.javawebinar.basejava.model;

import java.time.YearMonth;
import java.util.Objects;

public class OrganizationPeriod {
    private final YearMonth startDate;
    private final YearMonth endDate;
    private final String title;
    private final String description;

    public OrganizationPeriod(YearMonth startDate, YearMonth endDate, String title, String description) {
        Objects.requireNonNull(startDate, "startDate must not be null");
        Objects.requireNonNull(endDate, "endDate must not be null");
        Objects.requireNonNull(title, "title must not be null");
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationPeriod organizationPeriod = (OrganizationPeriod) o;

        if (!startDate.equals(organizationPeriod.startDate)) return false;
        if (!endDate.equals(organizationPeriod.endDate)) return false;
        if (!title.equals(organizationPeriod.title)) return false;
        return description != null ? description.equals(organizationPeriod.description) : organizationPeriod.description == null;
    }

    @Override
    public int hashCode() {
        int result = startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "\n" + startDate + " - " + endDate + "   " + title + "\n" + description;
    }
}