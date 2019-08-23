package ru.javawebinar.basejava.model;

import java.io.Serializable;
import java.time.YearMonth;
import java.util.*;

import static ru.javawebinar.basejava.util.DateUtil.NOW;

public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Link homePage;
    private final List<OrganizationPeriod> organizationPeriods;

    public Organization(String name, String url, OrganizationPeriod period) {
        this(name, url, new ArrayList<>(Collections.singletonList(period)));
    }

    public Organization(String name, String url, OrganizationPeriod... periods) {
        this(name, url, new ArrayList<>(Arrays.asList(periods)));
    }

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

    public static class OrganizationPeriod implements Serializable {
        private final YearMonth startDate;
        private final YearMonth endDate;
        private final String title;
        private final String description;

        public OrganizationPeriod(YearMonth startDate, String title, String description) {
            this(startDate, NOW, title, description);
        }

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
}