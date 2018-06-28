/**
 * com.urise.webapp.model.Resume class
 */
public class Resume {
    public Resume(String uuid) {
        this.uuid = uuid;
    }

    // Unique identifier
    String uuid;

    public Resume() {

    }

    @Override
    public String toString() {
        return uuid;
    }

}
