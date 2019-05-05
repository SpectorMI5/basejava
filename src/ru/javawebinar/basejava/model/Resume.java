package ru.javawebinar.basejava.model;

public class Resume {

    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        return uuid.equals(resume.uuid);

    }

    public int hashCode() {
        return uuid.hashCode();
    }

    public String toString() {
        return uuid;
    }
}
