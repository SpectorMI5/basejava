package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage<String> {
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
    protected void updateResume(String key, Resume resume) {
        map.replace(key, resume);
    }

    @Override
    protected void saveResume(String key, Resume resume) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getResume(String key) {
        return map.get(key);
    }

    @Override
    protected void deleteResume(String key) {
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
    protected boolean isExist(String key) {
        return map.containsKey(key);
    }
}