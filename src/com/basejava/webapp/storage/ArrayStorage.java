package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private static final int STORAGE_LIMIT = 10_000;
    private Resume[] storage = new Resume[STORAGE_LIMIT];

    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume r) {
        int i = getIndex(r.getUuid());
        if (i > -1) {
            storage[i] = r;
        } else {
            System.out.println("Резюме с uuid = " + r.getUuid() + " нет!");
        }
    }

    public void save(Resume r) {
        int i = getIndex(r.getUuid());
        if (size == STORAGE_LIMIT) {
            System.out.println("Массив резюме полон!");
        } else if (i == -1) {
            storage[size] = r;
            size++;
        } else {
            System.out.println("Резюме с uuid = " + r.getUuid() + " уже есть!");
        }
    }

    public Resume get(String uuid) {
        int i = getIndex(uuid);
        if (i > -1) {
            return storage[i];
        }
        System.out.println("Резюме с uuid = " + uuid + " нет!");
        return null;
    }

    public void delete(String uuid) {
        int i = getIndex(uuid);
        if (i > -1) {
            storage[i] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("Резюме с uuid = " + uuid + " нет!");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    private int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
