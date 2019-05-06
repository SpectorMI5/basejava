package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void saveResume(Resume r, int i) {
        i = Math.abs(i + 1);
        System.arraycopy(storage, i, storage, i + 1, size - i);
        storage[i] = r;
    }

    @Override
    protected void deleteResume(int i) {
        i++;
        System.arraycopy(storage, i, storage, i - 1, size - i);
    }
}
