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
    protected void updateResume(String index, Resume r) {
        map.replace(r.getUuid(), r);
    }

    @Override
    protected void saveResume(String index, Resume r) {
        map.put(r.getUuid(), r);
    }

    @Override
    protected Resume getResume(String index, String uuid) {
        return map.get(uuid);
    }

    @Override
    protected void deleteResume(String index, String uuid) {
        map.remove(uuid);
    }

    @Override
    protected String getIndex(String uuid) {
        for (HashMap.Entry<String, Resume> item : map.entrySet()) {
            if (uuid.equals(item.getKey())) {
                return item.getKey();
            }
        }
        return "-1";
    }

    @Override
    protected boolean checkIndex(String index) {
        return !index.equals("-1");
    }
}
