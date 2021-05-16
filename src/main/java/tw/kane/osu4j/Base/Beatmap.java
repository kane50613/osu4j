package tw.kane.osu4j.Base;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.json.JSONObject;
import tw.kane.osu4j.Mode;

import java.util.Date;

public class Beatmap {
    private final DocumentContext object;

    public String id, version;
    public float difficultyRating;
    public Mode mode;
    public long totalLength, hitLength, bpm;
    public int accuracy, ar, cs, drain;
    public Date lastUpdate;
    public boolean isRanked;

    public BeatmapSet beatmapSet;
    public Count counts;

    public Beatmap(String json) {
        object = JsonPath.parse(json);
    }

    public Beatmap(JSONObject json) {
        object = JsonPath.parse(json.toString());
    }

    public void init() {

    }

    public class Count {
        public long circles, sliders, spinners, passCount, playCount;
    }
}
