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
    private final DocumentContext object;

    public String id, name, country, avatarUrl;
    public int level;
    public float accuracy;
    public Date joinAt;
    public Count counts;
    public PlayScore scores;
    public PerformancePoint pp;

    boolean isActive, isBot, isDeleted, isOnline, isSupporter, hasSupported;


    public User(JSONObject json) {
        object = JsonPath.parse(json.toString());
        id = String.valueOf(JSON.get(object, "$.id", Integer.class, null));
        avatarUrl = JSON.get(object, "$.avatar_url", String.class, null);
        name = JSON.get(object, "$.username", String.class, null);
        country = JSON.get(object, "$.country.name", String.class,
                JSON.get(object, "$.country_code", String.class, null)
        );
        level = JSON.get(object, "$.statistics.level.current", Integer.class, 0);
        accuracy = JSON.get(object, "$.hit_accuracy", Float.class, 0f);

        isActive = JSON.get(object, "$.is_active", Boolean.class, false);
        isBot = JSON.get(object, "$.is_bot", Boolean.class, false);
        isDeleted = JSON.get(object, "$.is_deleted", Boolean.class, false);
        isOnline = JSON.get(object, "$.is_online", Boolean.class, false);
        isSupporter = JSON.get(object, "$.is_supporter", Boolean.class, false);
        hasSupported = JSON.get(object, "$.has_supported", Boolean.class, false);

        try {
            joinAt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").parse(JSON.get(object, "$.join_date", String.class, null));
        } catch (ParseException | NullPointerException e) {
            joinAt = null;
        }

        counts = new Count();
        scores = new PlayScore();
        pp = new PerformancePoint();
    }

    public class Count {
        public long SSH, SS, SH, S, A, plays, secondPlayed;

        public Count() {
            SSH = JSON.get(object, "$.statistics.grade_counts.ssh", Long.class, 0L);
            SH = JSON.get(object, "$.statistics.grade_counts.sh", Long.class, 0L);
            SS = JSON.get(object, "$.statistics.grade_counts.ss", Long.class, 0L);
            S = JSON.get(object, "$.statistics.grade_counts.s", Long.class, 0L);
            A = JSON.get(object, "$.statistics.grade_counts.a", Long.class, 0L);
            plays = JSON.get(object, "$.statistics.play_count", Long.class, 0L);
            secondPlayed = JSON.get(object, "$.statistics.play_time", Long.class, 0L);
        }
    }

    public class PlayScore {
        public long ranked, total;

        public PlayScore() {
            ranked = JSON.get(object, "$.statistics.rank.ranked_score", Long.class, 0L);
            total = JSON.get(object, "$.statistics.rank.total_score", Long.class, 0L);
        }
    }

    public class PerformancePoint {
        public long rank, countryRank, pp;

        public PerformancePoint() {
            pp = JSON.get(object, "$.statistics.pp", Long.class, 0L);
            rank = JSON.get(object, "$.statistics.rank.global", Long.class, 0L);
            countryRank = JSON.get(object, "$.statistics.rank.country", Long.class, 0L);
        }
    }
}
