package tw.kane.osu4j;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class User {
    private final JSONObject object;

    public String id, name, country;
    public float level, accuracy;
    public Date joinAt;
    public Count counts;
    public PlayScore scores;
    public PerformancePoint pp;


    public User(JSONObject object) {
        this.object = object;
        id = object.isNull("user_id") ? null : object.getString("user_id");
        name = object.isNull("username") ? null : object.getString("username");
        country = object.isNull("country") ? null : object.getString("country");

        level = object.isNull("level") ? 0 : Float.parseFloat(object.getString("level"));
        accuracy = object.isNull("accuracy") ? 0 : Float.parseFloat(object.getString("accuracy"));

        try {
            joinAt = object.isNull("join_date") ? null : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(object.getString("join_date"));
        } catch (ParseException e) {
            joinAt = null;
        }

        counts = new Count();
        scores = new PlayScore();
        pp = new PerformancePoint();
    }

    private class Count {
        int _50, _100, _300, SSH, SS, SH, S, A, plays, secondPlayed;

        public Count() {
            _50 = object.isNull("count50") ? 0 : Integer.parseInt(object.getString("count50"));
            _100 = object.isNull("count100") ? 0 : Integer.parseInt(object.getString("count100"));
            _300 = object.isNull("count300") ? 0 : Integer.parseInt(object.getString("count300"));

            SSH = object.isNull("count_rank_ssh") ? 0 : Integer.parseInt(object.getString("count_rank_ssh"));
            SS = object.isNull("count_rank_ss") ? 0 : Integer.parseInt(object.getString("count_rank_ss"));
            SH = object.isNull("count_rank_sh") ? 0 : Integer.parseInt(object.getString("count_rank_sh"));
            S = object.isNull("count_rank_s") ? 0 : Integer.parseInt(object.getString("count_rank_s"));
            A = object.isNull("count_rank_a") ? 0 : Integer.parseInt(object.getString("count_rank_a"));

            plays = object.isNull("playcount") ? 0 : Integer.parseInt(object.getString("playcount"));
            secondPlayed = object.isNull("total_second_played") ? 0 : Integer.parseInt(object.getString("total_seconds_played"));
        }
    }

    private class PlayScore {
        int ranked, total;

        public PlayScore() {
            ranked = object.isNull("ranked_score") ? 0 : Integer.parseInt(object.getString("ranked_score"));
            total = object.isNull("total_score") ? 0 : Integer.parseInt(object.getString("total_score"));
        }
    }

    private class PerformancePoint {
        float pp;
        int rank, countryRank;

        public PerformancePoint() {
            pp = object.isNull("pp_raw") ? 0 : Float.parseFloat(object.getString("pp_raw"));

            rank = object.isNull("pp_rank") ? 0 : Integer.parseInt(object.getString("pp_rank"));
            countryRank = object.isNull("pp_country_rank") ? 0 : Integer.parseInt(object.getString("pp_country_rank"));
        }
    }
}
