package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date; // TODO: You'll likely use this in this class

import static gitlet.Utils.join;
import static gitlet.Utils.writeObject;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit implements Dumpable, Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */
    public Commit(String message, Date date) {
        this.message = message;
        this.date = date;
    }

    public Commit(String message, Date date, Blobs[] blobs) {
        this.message = message;
        this.date = date;
        this.blobs = blobs;
    }

    /** The message of this Commit. */
    private String message;

    private Date date;

    private Blobs[] blobs;

    /* TODO: fill in the rest of this class. */

    public Blobs[] getBlobs() {
        return blobs;
    }

    public void setBlobs(Blobs[] blobs) {
        this.blobs = blobs;
    }

    public void saveCommit() {
        String s = Utils.sha1(this.toString());
        File shaDir = Utils.join(Repository.COMMIT_DIR, s.substring(0, 2));
        if (!shaDir.exists()) {
            shaDir.mkdir();
        }
        File sha = Utils.join(shaDir, s);
        writeObject(sha, this);
    }

    @Override
    public void dump() {
        System.out.println(this);
        System.out.println(Utils.sha1(this.toString()));
    }

    @Override
    public String toString() {
        return "Commit{" +
                "message='" + message + '\'' +
                ", date=" + date +
                ", blobs=" + Arrays.toString(blobs) +
                '}';
    }
}
