package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage<Resume> {
    private Map<String, Resume> map = new HashMap<>();

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    protected void updateResume(Resume key, Resume resume) {
        map.replace(resume.getUuid(), resume);
    }

    @Override
    protected void saveResume(Resume key, Resume resume) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getResume(Resume key) {
        return key;
    }

    @Override
    protected void deleteResume(Resume key) {
        map.remove(key.getUuid());
    }

    @Override
    protected List<Resume> getAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected boolean checkSearchKey(Resume resume) {
        return !map.containsValue(resume);
    }
}