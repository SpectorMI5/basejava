package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    public void clear() {

    }

    public void update(Resume r) {

    }

    public void save(Resume r) {

    }

    public void delete(String uuid) {

    }

    public Resume[] getAll() {
        return new Resume[0];
    }

    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
