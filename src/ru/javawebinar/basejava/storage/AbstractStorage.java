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

    @Override
    public void update(Resume resume) {
        Object searchKey = getContainsSearchKey(resume.getUuid());
        updateResume(searchKey, resume);
    }

    @Override
    public void save(Resume resume) {
        Object searchKey = getNotContainsSearchKey(resume.getUuid());
        saveResume(searchKey, resume);
    }

    @Override
    public Resume get(String uuid) {
        Object searchKey = getContainsSearchKey(uuid);
        return getResume(searchKey);
    }

    @Override
    public void delete(String uuid) {
        Object searchKey = getContainsSearchKey(uuid);
        deleteResume(searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = getAll();
        resumes.sort(Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid));
        return resumes;
    }

    private Object getContainsSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (checkSearchKey(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getNotContainsSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!checkSearchKey(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract void updateResume(Object searchKey, Resume resume);

    protected abstract void saveResume(Object searchKey, Resume resume);

    protected abstract Resume getResume(Object searchKey);

    protected abstract void deleteResume(Object searchKey);

    protected abstract List<Resume> getAll();

    protected abstract Object getSearchKey(String uuid);

    protected abstract boolean checkSearchKey(Object searchKey);
}