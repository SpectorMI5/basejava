package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected void updateResume(String index, Resume r) {
        storage[Integer.parseInt(index)] = r;
    }

    @Override
    public void saveResume(String index, Resume r) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        } else {
            saveResumeInArray(Integer.parseInt(index), r);
            size++;
        }
    }

    @Override
    protected Resume getResume(String index) {
        return storage[Integer.parseInt(index)];
    }

    @Override
    protected void deleteResume(String index) {
        deleteResumeInArray(Integer.parseInt(index));
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected boolean checkIndex(String index) {
        return !(Integer.parseInt(index) < 0);
    }

    protected abstract void saveResumeInArray(int index, Resume r);

    protected abstract void deleteResumeInArray(int i);

    protected abstract String getIndex(String uuid);
}
