package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    public void save(Resume r) {
        int i = size;
        Resume buf;
        if (size == STORAGE_LIMIT) {
            System.out.println("Массив резюме полон!");
        } else if (getIndex(r.getUuid()) > -1) {
            System.out.println("Резюме с id = " + r.getUuid() + " уже есть!");
        } else {
            storage[size] = r;
            if (size > 0) {
                while (storage[i].compareTo(storage[i - 1]) < 0) {
                    buf = storage[i];
                    storage[i] = storage[i - 1];
                    storage[i - 1] = buf;
                    i--;
                    if (i == 0) {
                        break;
                    }
                }
            }
            size++;
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("Резюме с id = " + uuid + " нет!");
        } else {
            for (index++; index < size; index++) {
                storage[index - 1] = storage[index];
            }
            storage[size - 1] = null;
            size--;
        }
    }

    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
