import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int i;

    void clear() {
        int a = size();
        for(i = 0; i < a; i++) {
            storage[i] = null;
        }
    }

    void save(Resume r) {
        size();
        storage[i] = r;
    }

    Resume get(String uuid) {
        int a = size();
        for(i = 0; i < a; i++) {
            if(storage[i].uuid == uuid) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        int a = size();
        for(i = 0; i < a; i++) {
            if(storage[i].uuid == uuid) {
                while(storage[i] != null) {
                    storage[i] = storage[i+1];
                    i++;
                }
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        size();
        return Arrays.copyOf(storage, i);
    }

    int size() {
        for(i = 0; i < storage.length; i++) {
            if(storage[i] == null) {
                break;
            }
        }
        return i;
    }
}
