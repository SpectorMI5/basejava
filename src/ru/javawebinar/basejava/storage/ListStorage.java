package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> list = new ArrayList<>();

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    protected void updateResume(Object index, Resume r) {
        list.set((Integer) index, r);
    }

    @Override
    protected void saveResume(Object index, Resume r) {
        list.add(r);
    }

    @Override
    protected Resume getResume(Object index) {
        return list.get((Integer) index);
    }

    @Override
    protected void deleteResume(Object index) {
        list.remove(((Integer) index).intValue());
    }

    @Override
    protected List<Resume> getAll() {
        return list;
    }

    @Override
    protected Integer getIndex(String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (uuid.equals(list.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean checkIndex(Object index) {
        return index.equals(-1);
    }
}
