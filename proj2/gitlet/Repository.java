package gitlet;

import java.io.File;
import java.util.Date;

import static gitlet.Utils.*;

// TODO: any imports you need here

/**
 * Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 * @author TODO
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /**
     * The current working directory.
     */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /**
     * The .gitlet directory.
     */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    public static final File COMMIT_DIR = join(GITLET_DIR, "commits");
    public static final File ADD_DIR = join(GITLET_DIR, "add");

    /* TODO: fill in the rest of this class. */

    public static boolean initCreate() {
        if (GITLET_DIR.exists()) {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
            return false;
        } else {
            GITLET_DIR.mkdir();
            COMMIT_DIR.mkdir();
            ADD_DIR.mkdir();
            commit("initial commit", new Date(), null);
            return true;
        }
    }

    public static void add(String file) {
        File joinBlob = join(CWD, file);
        if (joinBlob.exists()) {
            byte[] bytes = readContents(joinBlob);

        }
    }

    public static void commit(String message, Date date, Blobs[] blobs) {
        new Commit(message, date).saveCommit();
    }
}
