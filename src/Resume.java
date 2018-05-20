import java.util.Comparator;

/**
 * com.urise.webapp.model.Resume class
 */
public class Resume implements Comparator<Resume> {
    public Resume(String a)
    {
        uuid = a;
    }

    // Unique identifier
    String uuid;

    public Resume() {

    }

    @Override
    public int compare(Resume o, Resume o2) {
        return o2.uuid.compareTo(o.uuid);
    }
      @Override
    public String toString() {
        return uuid;
    }

}
