package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
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
    protected void updateResume(Integer index, Resume resume) {
        list.set(index, resume);
    }

    @Override
    protected void saveResume(Integer index, Resume resume) {
        list.add(resume);
    }

    @Override
    protected Resume getResume(Integer index) {
        return list.get(index);
    }

    @Override
    protected void deleteResume(Integer index) {
        list.remove(index.intValue());
    }

    @Override
    protected List<Resume> getAll() {
        return new ArrayList<>(list);
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (uuid.equals(list.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean checkSearchKey(Integer index) {
        return index.equals(-1);
    }
}