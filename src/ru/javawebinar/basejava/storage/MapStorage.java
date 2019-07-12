package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;

public class MapStorage extends AbstractStorage {
    private HashMap<Integer, Resume> map = new HashMap<>();

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
    protected int getIndex(String uuid) {
        for (HashMap.Entry<Integer, Resume> item : map.entrySet()) {
            if (uuid.equals(item.getValue().getUuid())) {
                return item.getKey();
            }
        }
        return -1;
    }

    @Override
    protected void updateResume(Resume r, int index) {
        map.replace(index, r);
    }

    @Override
    protected void saveResume(Resume r, int index) {
        index = Integer.parseInt(r.getUuid());
        map.put(index, r);
    }

    @Override
    protected Resume getResume(int index) {
        return map.get(index);
    }

    @Override
    protected void deleteResume(int index) {
        map.remove(index);
    }
}
