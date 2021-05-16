package tw.kane.osu4j.Base;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.json.JSONObject;
import tw.kane.osu4j.JSON;
import tw.kane.osu4j.Mode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Score {
    private final JSONObject _json;
    private final DocumentContext object;

    public String scoreId;
    public long score;
    public float accuracy, pp;
    public boolean isPerfect;
    public String[] mods;
    public Rank rank;
    public Date playAt;
    public Mode mode;

    public Count counts;
    public User user;
    public Beatmap beatmap;

    public Score(JSONObject json) {
        _json = json;
        object = JsonPath.parse(json.toString());
        init();
    }

    public Score(String json) {
        _json = new JSONObject(json);
        object = JsonPath.parse(json);
        init();
    }

    public void init() {
        scoreId = String.valueOf(JSON.get(object, "$.id", Integer.class, 0));
        score = JSON.get(object, "$.score", Long.class, 0L);
        isPerfect = JSON.get(object, "$.perfect", Boolean.class, false);
        accuracy = JSON.get(object, "$.accuracy", Float.class, 0f);
        pp = JSON.get(object, "$.pp", Float.class, 0f);
        mode = Mode.valueOf(JSON.get(object, "$.mode", String.class, "osu").toUpperCase());
        mods = _json.getJSONArray("mods").toList().toArray(new String[0]);

        try {
            playAt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").parse(JSON.get(object, "$.created_at", String.class, null));
        } catch (ParseException ignored) { }

        rank = Rank.valueOf(JSON.get(object, "$.rank", String.class, "F"));
        counts = new Count();
        beatmap = new Beatmap(_json.getJSONObject("beatmap"));
        user = new User(_json.getJSONObject("user"));
    }

    public class Count {
        public long _300, _100, _50, miss, maxCombo;

        public Count() {
            _300 = JSON.get(object, "$.statistics.count_300", Long.class, 0L);
            _100 = JSON.get(object, "$.statistics.count_100", Long.class, 0L);
            _50 = JSON.get(object, "$.statistics.count_50", Long.class, 0L);
            miss = JSON.get(object, "$.statistics.count_miss", Long.class, 0L);
            maxCombo = JSON.get(object, "$.max_combo", Long.class, 0L);
        }
    }

    public enum Rank {
        SSH("SS+"),
        SH("S+"),
        SS("SS"),
        S("S"),
        A("A"),
        B("B"),
        C("C"),
        D("D"),
        F("F");

        private final String name;

        Rank(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
