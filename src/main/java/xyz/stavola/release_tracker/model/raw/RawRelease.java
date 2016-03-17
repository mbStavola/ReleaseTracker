package xyz.stavola.release_tracker.model.raw;

import java.util.ArrayList;
import java.util.List;

public class RawRelease {
    public String name;
    public String tag_name;
    public String html_url;
    public String target_commitish;
    public boolean draft;
    public RawAuthor author;
    public boolean prerelease;
    public String created_at;
    public String published_at;
    public List<RawAsset> assets = new ArrayList<>();
    public String tarball_url;
    public String zipball_url;
}
