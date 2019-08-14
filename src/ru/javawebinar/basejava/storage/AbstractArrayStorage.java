package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
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
    protected void updateResume(Integer index, Resume resume) {
        storage[index] = resume;
    }

    @Override
    public void saveResume(Integer index, Resume resume) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else {
            saveResumeInArray(index, resume);
            size++;
        }
    }

    @Override
    protected Resume getResume(Integer index) {
        return storage[index];
    }

    @Override
    protected void deleteResume(Integer index) {
        deleteResumeInArray(index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected List<Resume> getAll() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    @Override
    protected boolean isExist(Integer index) {
        return !(index < 0);
    }

    protected abstract void saveResumeInArray(int index, Resume resume);

    protected abstract void deleteResumeInArray(int index);
}