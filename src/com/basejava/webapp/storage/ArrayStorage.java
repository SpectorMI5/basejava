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
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(r.getUuid())) {
                storage[i] = r;
                break;
            }
            if (i == size - 1) {
                System.out.println("Резюме с uuid = " + r.getUuid() + " нет!");
            }

        }
    }

    public void save(Resume r) {
        int i;
        if (size == storage.length) {
            System.out.println("Массив резюме полон!");
        } else {
            for (i = 0; i < size; i++) {
                if (storage[i].getUuid().equals(r.getUuid())) {
                    System.out.println("Резюме с uuid = " + r.getUuid() + " уже есть!");
                    break;
                }
            }
            if (i == size) {
                storage[size] = r;
                size++;
            }
        }
    }

    public Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return storage[i];
            }
        }
        System.out.println("Резюме с uuid = " + uuid + " нет!");
        return null;
    }

    public void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                storage[i] = storage[size - 1];
                storage[size - 1] = null;
                size--;
                break;
            }
            if (i == size - 1) {
                System.out.println("Резюме с uuid = " + uuid + " нет!");
            }
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
}
