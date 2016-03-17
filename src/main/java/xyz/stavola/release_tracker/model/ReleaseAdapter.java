package xyz.stavola.release_tracker.model;

import com.squareup.moshi.FromJson;
import xyz.stavola.release_tracker.model.raw.RawAsset;
import xyz.stavola.release_tracker.model.raw.RawAuthor;
import xyz.stavola.release_tracker.model.raw.RawRelease;
import xyz.stavola.release_tracker.model.transformed.*;

import java.util.List;

public class ReleaseAdapter {
    @FromJson
    Release transformRawRelease(RawRelease raw) {
        Release release = new Release();

        release.name = raw.name;
        release.tag = raw.tag_name;

        release.targetCommit = raw.target_commitish;
        release.url = raw.html_url;

        release.author = transformRawAuthor(raw.author);

        ReleaseTable releaseTable = new ReleaseTable();

        releaseTable.isPrerelease = raw.prerelease ? "Yes" : "No";
        releaseTable.isDraft = raw.draft ? "Yes" : "No";

        releaseTable.created = raw.created_at;
        releaseTable.published = raw.published_at;

        release.releaseTable = releaseTable;

        release.assets = convertRawAssetList(raw.assets);

        release.zipUrl = raw.zipball_url;
        release.tarUrl = raw.tarball_url;

        return  release;
    }

    private Asset[] convertRawAssetList(List<RawAsset> rawAssets) {
        Asset[] assets = new Asset[rawAssets.size()];

        for(int i = 0; i < rawAssets.size(); i++) {
            assets[i] = transformRawAsset(rawAssets.get(i));
        }

        return assets;
    }

    private Asset transformRawAsset(RawAsset raw) {
        Asset asset = new Asset();

        asset.uploader = transformRawAuthor(raw.uploader);

        AssetTable assetTable = new AssetTable();

        assetTable.name = raw.name;
        assetTable.label = raw.label;

        assetTable.state = raw.state;

        assetTable.created = raw.created_at;
        assetTable.updated = raw.updated_at;

        assetTable.size = humanReadableByteCount(raw.size, true);
        assetTable.downloadCount = raw.download_count;

        asset.assetTable = assetTable;

        asset.url = raw.browser_download_url;

        return asset;
    }

    private Author transformRawAuthor(RawAuthor rawAuthor) {
        Author author = new Author();

        author.name = rawAuthor.login;
        author.avatar = rawAuthor.avatar_url;
        author.url = rawAuthor.html_url;

        return author;
    }

    //http://stackoverflow.com/questions/3758606/how-to-convert-byte-size-into-human-readable-format-in-java
    private String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }
}
