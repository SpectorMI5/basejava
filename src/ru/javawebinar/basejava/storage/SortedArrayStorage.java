package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    protected void sortingStorage() {
        boolean isSorted = false;
        Resume buf;
        while (!isSorted) {
            isSorted = true;
            for (int i = 1; i < size; i++) {
                if ((storage[i].getUuid()).compareTo(storage[i - 1].getUuid()) < 0) {
                    isSorted = false;
                    buf = storage[i];
                    storage[i] = storage[i - 1];
                    storage[i - 1] = buf;
                }
            }
        }
    }
}
