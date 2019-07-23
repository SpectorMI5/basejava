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
    protected void updateResume(Object index, Resume r) {
        map.replace(r.getUuid(), r);
    }

    @Override
    protected void saveResume(Object index, Resume r) {
        map.put(r.getUuid(), r);
    }

    @Override
    protected Resume getResume(Object index) {
        return (Resume) index;
    }

    @Override
    protected void deleteResume(Object index) {
        map.remove(((Resume) index).getUuid());
    }

    @Override
    protected List<Resume> getAll() {
        return Arrays.asList(map.values().toArray(new Resume[map.size()]));
    }

    @Override
    protected Resume getIndex(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected boolean checkIndex(Object index) {
        return !map.containsValue(index);
    }
}