package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;

public class MapStorage extends AbstractStorage {
    private HashMap<String, Resume> map = new HashMap<>();

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public void update(Resume r) {
        boolean containsResume = containsResume(r);
        if (!containsResume) {
            throw new NotExistStorageException(r.getUuid());
        } else {
            map.replace(r.getUuid(), r);
        }
    }

    @Override
    public void save(Resume r) {
        boolean containsResume = containsResume(r);
        if (containsResume) {
            throw new ExistStorageException(r.getUuid());
        } else {
            map.put(r.getUuid(), r);
        }
    }

    @Override
    public Resume get(String uuid) {
        Resume r = searchResume(uuid);
        if (r == null) {
            throw new NotExistStorageException(uuid);
        } else {
            return r;
        }
    }

    @Override
    public void delete(String uuid) {
        Resume r = searchResume(uuid);
        if (r == null) {
            throw new NotExistStorageException(uuid);
        } else {
            map.remove(uuid, r);
        }
    }

    @Override
    public Resume[] getAll() {
        return map.values().toArray(new Resume[map.size()]);
    }

    @Override
    public int size() {
        return map.size();
    }

    private boolean containsResume(Resume r) {
        return map.containsValue(r);
    }

    private Resume searchResume(String uuid) {
        for (HashMap.Entry<String, Resume> item : map.entrySet()) {
            if (uuid.equals(item.getValue().getUuid())) {
                return item.getValue();
            }
        }
        return null;
    }

    @Override
    protected int getIndex(String uuid) {
        return 0;
    }

    @Override
    protected void updateResume(Resume r, int index) {
    }

    @Override
    protected void saveResume(Resume r, int index) {
    }

    @Override
    protected Resume getResume(int index) {
        return null;
    }

    @Override
    protected void deleteResume(int index) {
    }
}
