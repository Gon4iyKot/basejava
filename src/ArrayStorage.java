import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int counter = 0;

    void clear() {
        storage = new Resume[10000];
        counter =0;
    }

    void save(Resume r) {
        storage [counter] = r;
        counter++;
    }

    Resume get(String uuid) {
        Resume fin = null;
        for(Resume temp:storage) {
            if(temp!=null) {
                if (temp.uuid.equals(uuid))
                    fin = temp;
            }
        }
        return fin;
    }

    void delete(String uuid) {
        for(int i =0; i<storage.length; i++){
            if (storage[i]!=null){
                if (storage[i].uuid.equals(uuid)){
                    storage[i] = null;
                    break;
                }
            }
        }
        storage=ShityShifter(storage);
        counter--;

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

    private Resume[] ShityShifter(Resume[] r) {
        int mas=0;
        Resume [] fin = new Resume[r.length];
        for (Resume aR : r) {
            if (aR != null) {
                fin[mas] = aR;
                mas++;
            }
        }
        return fin;
    }
}
