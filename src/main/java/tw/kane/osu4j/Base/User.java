package tw.kane.osu4j.Base;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.json.JSONObject;
import tw.kane.osu4j.JSON;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class User {
    private DocumentContext object;

    public String id, name, country;
    public int level;
    public float accuracy;
    public Date joinAt;
    public Count counts;
    public PlayScore scores;
    public PerformancePoint pp;

    boolean isActive, isBot, isDeleted, isOnline, isSupporter, hasSupported;


    public User(JSONObject json) {
        object = JsonPath.parse(json.toString());
        id = String.valueOf(JSON.get(object, "id", Integer.class, null));
        name = JSON.get(object, "username", String.class, null);
        country = JSON.get(object, "country.name", String.class,
                JSON.get(object, "country_code", String.class, null)
        );

        level = JSON.get(object, "statistics.level.current", Integer.class, 0);
        accuracy = JSON.get(object, "hit_accuracy", Float.class, 0f);

        try {
            joinAt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(JSON.get(object, "$.join_date", String.class, null));
        } catch (ParseException | NullPointerException e) {
            joinAt = null;
        }

        counts = new Count();
        scores = new PlayScore();
        pp = new PerformancePoint();
    }

    private class Count {
        long _50, _100, _300, SSH, SS, SH, S, A, plays, secondPlayed;

        public Count() {
        }
    }

    private class PlayScore {
        long ranked, total;

        public PlayScore() {
        }
    }

    private class PerformancePoint {
        float pp;
        long rank, countryRank;

        public PerformancePoint() {
        }
    }
}
