package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {
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
        map.replace(resume.getUuid(), resume);
    }

    @Override
    protected void saveResume(Object key, Resume resume) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getResume(Object key) {
        return (Resume) key;
    }

    @Override
    protected void deleteResume(Object key) {
        map.remove(((Resume) key).getUuid());
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
    protected boolean checkSearchKey(Object resume) {
        return !map.containsValue(resume);
    }
}