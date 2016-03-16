package xyz.stavola.release_tracker.model.api;

import java.util.ArrayList;
import java.util.List;

public class Release {
    public String url;
    public String assetsUrl;
    public String uploadUrl;
    public String htmlUrl;
    public Integer id;
    public String tagName;
    public String targetCommitish;
    public String name;
    public Boolean draft;
    public Author author;
    public Boolean prerelease;
    public String createdAt;
    public String publishedAt;
    public List<Asset> assets = new ArrayList<Asset>();
    public String tarballUrl;
    public String zipballUrl;
    public String body;
}
