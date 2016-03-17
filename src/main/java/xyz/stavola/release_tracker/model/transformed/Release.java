package xyz.stavola.release_tracker.model.transformed;

public class Release {
    public String name;
    public String tag;

    public String targetCommit;
    public String url;

    public Author author;

    public ReleaseTable releaseTable;

    public Asset[] assets;

    public String zipUrl;
    public String tarUrl;
}

