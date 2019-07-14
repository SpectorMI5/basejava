package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveResumeInArray(int index, Resume r) {
        storage[size] = r;
    }

    @Override
    protected void deleteResumeInArray(int index) {
        storage[index] = storage[size - 1];
    }

    @Override
    protected String getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return "" + i;
            }
        }
        return "-1";
    }

    @Override
    protected boolean checkIndex(String index) {
        return !index.equals("-1");
    }
}
