package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;

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
    public int size() {
        return size;
    }

    @Override
    protected void updateResume(Object index, Resume r) {
        storage[(Integer) index] = r;
    }

    @Override
    public void saveResume(Object index, Resume r) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        } else {
            saveResumeInArray((Integer) index, r);
            size++;
        }
    }

    @Override
    protected Resume getResume(Object index) {
        return storage[(Integer) index];
    }

    @Override
    protected void deleteResume(Object index) {
        deleteResumeInArray((Integer) index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected List<Resume> getAll() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, size));
    }

    @Override
    protected boolean checkIndex(Object index) {
        return ((Integer) index < 0);
    }

    protected abstract void saveResumeInArray(int index, Resume r);

    protected abstract void deleteResumeInArray(int index);

    protected abstract Integer getIndex(String uuid);
}
