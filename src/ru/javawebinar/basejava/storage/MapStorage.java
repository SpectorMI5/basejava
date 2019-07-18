package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private Map<String, Resume> map = new HashMap<>();

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Resume[] getAll() {
        return map.values().toArray(new Resume[map.size()]);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    protected void updateResume(Object index, Resume r) {
        map.replace((String) index, r);
    }

    @Override
    protected void saveResume(Object index, Resume r) {
        map.put(r.getUuid(), r);
    }

    @Override
    protected Resume getResume(Object index) {
        return map.get(index);
    }

    @Override
    protected void deleteResume(Object index) {
        map.remove(index);
    }

    @Override
    protected String getIndex(String uuid) {
        return uuid;
    }

    @Override
    protected boolean checkIndex(Object index) {
        return !map.containsKey(index);
    }
}
