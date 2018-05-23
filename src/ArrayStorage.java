import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int counter = 0;

    void clear() {
        storage = new Resume[10000];
        counter = 0;
    }

    void save(Resume r) {
        storage[counter] = r;
        counter++;
    }

    Resume get(String uuid) {
        Resume fin = null;
        for (int i = 0; i < counter; i++) {
            if (storage[i].uuid.equals(uuid))
                fin = storage[i];
        }
        return fin;
    }

    void delete(String uuid) {
        for (int i = 0; i < counter; i++) {
            if (storage[i].uuid.equals(uuid)) {
                System.arraycopy(Arrays.copyOfRange(storage, 0, storage.length), i + 1, storage, i, storage.length - i - 1);
                counter--;
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, counter);
    }

    int size() {
        return counter;
    }

    /* private Resume[] ShityShifter(Resume[] r) {
        int mas = 0;
        Resume[] fin = new Resume[r.length];
        for (Resume aR : r) {
            if (aR != null) {
                fin[mas] = aR;
                mas++;
            }
        }
        return fin;
    }
    */
}
