package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
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
    public void save(Resume r) {
        int index = getIndex(r.getUuid());
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        } else if (index > -1) {
            throw new ExistStorageException(r.getUuid());
        } else {
            saveResume(r, index);
            size++;
        }
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
    protected void updateResume(Resume r, int index) {
        storage[index] = r;
    }

    @Override
    protected Resume getResume(int index) {
        return storage[index];
    }

    @Override
    protected void deleteResume(int index) {
        deleteResumeInArray(index);
        storage[size - 1] = null;
        size--;
    }

    protected abstract int getIndex(String uuid);

    protected abstract void saveResume(Resume r, int index);

    protected abstract void deleteResumeInArray(int index);
}
