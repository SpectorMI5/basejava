package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

/**
 * Collection based storage for Resumes
 */
public abstract class AbstractStorage implements Storage {

    public void update(Resume r) {
        String index = getIndex(r.getUuid());
        if (!checkIndex(index)) {
            throw new NotExistStorageException(r.getUuid());
        } else {
            updateResume(index, r);
        }
    }

    public void save(Resume r) {
        String index = getIndex(r.getUuid());
        if (checkIndex(index)) {
            throw new ExistStorageException(r.getUuid());
        } else {
            saveResume(index, r);
        }
    }

    public Resume get(String uuid) {
        String index = getIndex(uuid);
        if (!checkIndex(index)) {
            throw new NotExistStorageException(uuid);
        }
        return getResume(index, uuid);
    }

    public void delete(String uuid) {
        String index = getIndex(uuid);
        if (!checkIndex(index)) {
            throw new NotExistStorageException(uuid);
        } else {
            deleteResume(index, uuid);
        }
    }

    public abstract void clear();

    public abstract Resume[] getAll();

    public abstract int size();

    protected abstract void updateResume(String index, Resume r);

    protected abstract void saveResume(String index, Resume r);

    protected abstract Resume getResume(String index, String uuid);

    protected abstract void deleteResume(String index, String uuid);

    protected abstract String getIndex(String uuid);

    protected abstract boolean checkIndex(String index);
}