package tw.kane.osu4j.Base;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.json.JSONObject;
import tw.kane.osu4j.JSON;
import tw.kane.osu4j.RankedStatus;

public class BeatmapSet {
    private final DocumentContext object;

    public String id, title, titleUnicode, artist, artistUnicode, creator, creatorId, previewUrl, cover;
    public boolean isNSFW, hasVideo;

    public RankedStatus rankedStatus;

    public Count counts;

    public BeatmapSet(String json) {
        object = JsonPath.parse(json);
        init();
    }

    public BeatmapSet(JSONObject json) {
        object = JsonPath.parse(json.toString());
        init();
    }

    public void init() {
        id = String.valueOf(JSON.get(object, "$.id", Integer.class, 0));
        title = JSON.get(object, "$.title", String.class, null);
        titleUnicode = JSON.get(object, "$.title_unicode", String.class, null);
        artist = JSON.get(object, "$.artist", String.class, null);
        artistUnicode = JSON.get(object, "$.artist_unicode", String.class, null);
        creator = JSON.get(object, "$.creator", String.class, null);
        creatorId = JSON.get(object, "$.user_id", String.class, null);
        previewUrl = JSON.get(object, "$.preview_url", String.class, null);
        isNSFW = JSON.get(object, "$.nsfw", Boolean.class, false);
        hasVideo = JSON.get(object, "$.video", Boolean.class, false);
        cover = JSON.get(object, "$.covers.cover", String.class, null);

        rankedStatus = RankedStatus.valueOf(JSON.get(object, "$.status", String.class, "pending").toUpperCase());

        counts = new Count();
    }

    public class Count {
        public long favouriteCount, playCount;

        public Count() {
            favouriteCount = JSON.get(object, "$.favourite_count", Long.class, 0L);
            playCount = JSON.get(object, "$.play_count", Long.class, 0L);
        }
    }
}
