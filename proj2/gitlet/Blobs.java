package gitlet;

import java.io.File;
import java.io.Serializable;

import static gitlet.Utils.readContents;

public class Blobs implements Dumpable, Serializable {
    private String sha1;
    private File file;

    public static Blobs getBlobs(File file) {
        return new Blobs(Utils.sha1(Utils.readContents(file)), file);
    }

    private Blobs(String sha1, File file) {
        this.sha1 = sha1;
        this.file = file;
    }

    @Override
    public void dump() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "Blobs{" +
                "sha1='" + sha1 + '\'' +
                ", file=" + file +
                '}';
    }
}
