package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage {
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
    protected void updateResume(Object key, Resume resume) {
        map.replace((String) key, resume);
    }

    @Override
    protected void saveResume(Object key, Resume resume) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getResume(Object key) {
        return map.get(key);
    }

    @Override
    protected void deleteResume(Object key) {
        map.remove(key);
    }

    @Override
    protected List<Resume> getAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean checkSearchKey(Object key) {
        return !map.containsKey(key);
    }
}