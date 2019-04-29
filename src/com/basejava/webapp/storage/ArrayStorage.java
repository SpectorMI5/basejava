package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10_000];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume r) {
        int i = findUuid(r.getUuid());
        if (i != size) {
            storage[i] = r;
        } else {
            System.out.println("Резюме с uuid = " + r.getUuid() + " нет!");
        }
    }

    public void save(Resume r) {
        int i = findUuid(r.getUuid());
        if (size == storage.length) {
            System.out.println("Массив резюме полон!");
        } else if (i == size) {
            storage[size] = r;
            size++;
        } else {
            System.out.println("Резюме с uuid = " + r.getUuid() + " уже есть!");
        }
    }

    public Resume get(String uuid) {
        int i = findUuid(uuid);
        if (i != size) {
            return storage[i];
        }
        System.out.println("Резюме с uuid = " + uuid + " нет!");
        return null;
    }

    public void delete(String uuid) {
        int i = findUuid(uuid);
        if (i != size) {
            storage[i] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("Резюме с uuid = " + uuid + " нет!");
        }
    }

    private int findUuid(String uuid) {
        int i;
        for (i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                break;
            }
        }
        return i;
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
}
