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
        if (checker(r.uuid) == null) {
            storage[counter] = r;
            counter++;
        } else {
            System.out.println("Резюме не существует, попробуйте в другой раз");
        }
    }

    void update(String uuid, String uuid2) {
        if (checker(uuid) != null) {
            storage[getId(uuid)] = new Resume(uuid2);
        } else {
            System.out.println("Резюме не существует, попробуйте в другой раз");
        }

    }

    Resume get(String uuid) {
        if (checker(uuid) != null) {
            return checker(uuid);
        } else {
            System.out.println("Резюме не существует, попробуйте в другой раз");
            return null;
        }
    }

    void delete(String uuid) {
        if (checker(uuid) != null) {
            System.arraycopy(storage, getId(uuid) + 1, storage, getId(uuid), storage.length - getId(uuid) - 1);
            counter--;
        } else {
            System.out.println("Резюме не существует, попробуйте в другой раз");
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

    private Resume checker(String id) {
        for (int i = 0; i < counter; i++) {
            if (storage[i].uuid.equals(id)) {
                return storage[i];
            }
        }
        return null;
    }

    private Integer getId(String id) {
        for (int i = 0; i < counter; i++) {
            if (storage[i].uuid.equals(id)) {
                return i;
            }
        }
        return null;
    }

}
