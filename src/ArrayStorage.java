import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int counter = 0;

    void clear() {
        Arrays.fill(storage, 0, counter - 1, null);
        counter = 0;
    }

    void save(Resume resume) {
        if (getId(resume.uuid) == null) {
            storage[counter] = resume;
            counter++;
        } else {
            System.out.println("Резюме уже существует, попробуйте в другой раз");
        }
    }

    void update(String uuid, String uuid2) {
        if (getId(uuid) != null) {
            storage[getId(uuid)] = new Resume(uuid2);
        } else {
            System.out.println("Резюме не существует, попробуйте в другой раз");
        }

    }

    Resume get(String uuid) {
        if (getId(uuid) != null) {
            return storage[getId(uuid)];
        } else {
            System.out.println("Резюме не существует, попробуйте в другой раз");
            return null;
        }
    }

    void delete(String uuid) {
        if (getId(uuid) != null) {
            storage[getId(uuid)] = storage[counter - 1];
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

    private Integer getId(String id) {
        for (int i = 0; i < counter; i++) {
            if (storage[i].uuid.equals(id)) {
                return i;
            }
        }
        return null;
    }

}
