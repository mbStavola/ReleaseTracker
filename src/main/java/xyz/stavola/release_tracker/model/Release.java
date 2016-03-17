package xyz.stavola.release_tracker.model;

import java.util.ArrayList;
import java.util.List;

public class Release {
    public String html_url;
    public String tag_name;
    public String target_commitish;
    public String name;
    public Boolean draft;
    public Author author;
    public Boolean prerelease;
    public String created_at;
    public String published_at;
    public List<Asset> assets = new ArrayList<Asset>();
    public String tarball_url;
    public String zipball_url;
}
