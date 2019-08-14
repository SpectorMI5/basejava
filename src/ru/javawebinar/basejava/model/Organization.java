package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class Organization {
    private final Link homePage;
    private final List<OrganizationPeriod> organizationPeriods;

    public Organization(String name, String url, ArrayList<OrganizationPeriod> organizationPeriods) {
        this.homePage = new Link(name, url);
        this.organizationPeriods = organizationPeriods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!homePage.equals(that.homePage)) return false;
        return organizationPeriods.equals(that.organizationPeriods);
    }

    @Override
    public int hashCode() {
        int result = homePage.hashCode();
        result = 31 * result + organizationPeriods.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "\n" + homePage + "\n" + organizationPeriods;
    }
}