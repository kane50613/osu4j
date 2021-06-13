package tw.kane.osu4j.Base;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.json.JSONObject;
import tw.kane.osu4j.JSON;
import tw.kane.osu4j.Mode;
import tw.kane.osu4j.RankedStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Beatmap {
    private final DocumentContext object;

    public String id, version;
    public float difficultyRating;
    public long totalLength, hitLength, bpm;
    public int accuracy, ar, cs, drain;
    public Date lastUpdate;
    public boolean isRanked;

    public Mode mode;
    public RankedStatus rankedStatus;
    public Count counts;

    public Beatmap(String json) {
        object = JsonPath.parse(json);
        init();
    }

    public Beatmap(JSONObject json) {
        object = JsonPath.parse(json.toString());
        init();
    }

    public void init() {
        id = String.valueOf(JSON.get(object, "$.id", Integer.class, 0));
        version = JSON.get(object, "$.version", String.class, null);
        difficultyRating = JSON.get(object, "$.difficulty_rating", Float.class, 0F);
        totalLength = JSON.get(object, "$.total_length", Long.class, 0L);
        hitLength = JSON.get(object, "$.hit_length", Long.class, 0L);
        accuracy = JSON.get(object, "$.accuracy", Integer.class, 0);
        ar = JSON.get(object, "$.ar", Integer.class, 0);
        cs = JSON.get(object, "$.cs", Integer.class, 0);
        drain = JSON.get(object, "$.drain", Integer.class, 0);
        bpm = JSON.get(object, "$.bpm", Integer.class, 0);
        isRanked = JSON.get(object, "$.ranked", Integer.class, 0) == 1;
        mode = Mode.valueOf(JSON.get(object, "$.mode", String.class, "standard").toUpperCase());
        rankedStatus = RankedStatus.valueOf(JSON.get(object, "$.status", String.class, "pending").toUpperCase());

        try {
            lastUpdate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").parse(JSON.get(object, "$.last_updated", String.class, null));
        } catch (ParseException ignored) { }

        counts = new Count();
    }

    public class Count {
        public long circles, sliders, spinners, passCount, playCount;

        public Count() {
            circles = JSON.get(object, "$.count_circles", Long.class, 0L);
            sliders = JSON.get(object, "$.count_sliders", Long.class, 0L);
            spinners = JSON.get(object, "$.count_spinners", Long.class, 0L);
            passCount = JSON.get(object, "$.passcount", Long.class, 0L);
            playCount = JSON.get(object, "$.playcount", Long.class, 0L);
        }
    }
}
