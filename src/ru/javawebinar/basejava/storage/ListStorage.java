package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {
    private ArrayList<Resume> list = new ArrayList<>();

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public void save(Resume r) {
        int index = getIndex(r.getUuid());
        if (index > -1) {
            throw new ExistStorageException(r.getUuid());
        } else {
            list.add(r);
        }
    }

    @Override
    public Resume[] getAll() {
        return new Resume[list.size()];
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (uuid.equals(list.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void updateResume(Resume r, int index) {
        list.set(index, r);
    }

    @Override
    protected Resume getResume(int index) {
        return list.get(index);
    }

    @Override
    protected void deleteResume(int index) {
        list.remove(index);
    }
}
