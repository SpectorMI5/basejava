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
    public Resume[] getAll() {
        return list.toArray(new Resume[list.size()]);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    protected void updateResume(String index, Resume r) {
        list.set(Integer.parseInt(index), r);
    }

    @Override
    protected void saveResume(String index, Resume r) {
        list.add(r);
    }

    @Override
    protected Resume getResume(String index) {
        return list.get(Integer.parseInt(index));
    }

    @Override
    protected void deleteResume(String index) {
        list.remove(Integer.parseInt(index));
    }

    @Override
    protected String getIndex(String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (uuid.equals(list.get(i).getUuid())) {
                return "" + i;
            }
        }
        return "-1";
    }

    @Override
    protected boolean checkIndex(String index) {
        return !index.equals("-1");
    }
}
