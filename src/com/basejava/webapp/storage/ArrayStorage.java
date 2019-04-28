package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume r) {
        int i;
        boolean b = false;
        for (i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(r.getUuid())) {
                b = true;
                break;
            }
        }
        if (b) {
            storage[i] = r;
        } else {
            System.out.println("Резюме с uuid = " + r.getUuid() + " нет!");
        }
    }

    public void save(Resume r) {
        boolean b = false;
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(r.getUuid())) {
                b = true;
                break;
            }
        }
        if (b) {
            System.out.println("Резюме с uuid = " + r.getUuid() + " уже есть!");
        } else if (size < 10000) {
            storage[size] = r;
            size++;
        } else {
            System.out.println("Массив резюме полон!");
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
        boolean b = false;
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                while (storage[i] != null) {
                    storage[i] = storage[i+1];
                    i++;
                }
                b = true;
                size--;
                break;
            }
        }
        if (!b) {
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
}
