package tw.kane.osu4j.Base;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.json.JSONObject;
import tw.kane.osu4j.RankedStatus;

public class BeatmapSet {
    private final DocumentContext object;

    public String id, artist, artistUnicode, creator, creatorId;
    public long favouriteCount, playCount;
    public boolean isNSFW;

    public RankedStatus rankedStatus;

    public BeatmapSet(String json) {
        object = JsonPath.parse(json);
    }

    public BeatmapSet(JSONObject json) {
        object = JsonPath.parse(json.toString());
    }

    public void init() {

    }


}
