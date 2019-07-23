package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Comparator;
import java.util.List;

/**
 * Collection based storage for Resumes
 */
public abstract class AbstractStorage implements Storage {

    public void update(Resume r) {
        Object index = getIndex(r.getUuid());
        if (checkIndex(index)) {
            throw new NotExistStorageException(r.getUuid());
        } else {
            updateResume(index, r);
        }
    }

    public void save(Resume r) {
        Object index = getIndex(r.getUuid());
        if (!checkIndex(index)) {
            throw new ExistStorageException(r.getUuid());
        } else {
            saveResume(index, r);
        }
    }

    public Resume get(String uuid) {
        Object index = getIndex(uuid);
        if (checkIndex(index)) {
            throw new NotExistStorageException(uuid);
        }
        return getResume(index);
    }

    public void delete(String uuid) {
        Object index = getIndex(uuid);
        if (checkIndex(index)) {
            throw new NotExistStorageException(uuid);
        } else {
            deleteResume(index);
        }
    }

    public List<Resume> getAllSorted() {
        List<Resume> list = getAll();
        list.sort(Comparator.comparing(Resume::getUuid));
        list.sort(Comparator.comparing(Resume::getFullName));
        return list;
    }


    public abstract void clear();

    public abstract int size();

    protected abstract void updateResume(Object index, Resume r);

    protected abstract void saveResume(Object index, Resume r);

    protected abstract Resume getResume(Object index);

    protected abstract void deleteResume(Object index);

    protected abstract List<Resume> getAll();

    protected abstract Object getIndex(String uuid);

    protected abstract boolean checkIndex(Object index);
}