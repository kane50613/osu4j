package tw.kane.osu4j;

import org.json.JSONObject;
import tw.kane.osu4j.Exception.InvalidTokenException;
import tw.kane.osu4j.Exception.NotFoundException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Score {
    private final JSONObject object;

    public String mapId, userId, scoreId;
    public long score;
    public boolean isPerfect;
    public Mod[] mods;
    public Rank rank;
    public Date playAt;

    public Count counts;

    public Score(JSONObject object) {
        this.object = object;

        isPerfect = !object.isNull("perfect") && Boolean.parseBoolean(object.getString("perfect"));
        mapId = object.isNull("beatmap_id") ? null : object.getString("beatmap_id");
        userId = object.isNull("user_id") ? null : object.getString("user_id");
        scoreId = object.isNull("score_id") ? null : object.getString("score_id");
        rank = object.isNull("rank") ? null : Rank.valueOf(object.getString("rank"));
        score = object.isNull("score") ? 0 : Long.parseLong(object.getString("score"));

        try {
            playAt = object.isNull("date") ? null : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(object.getString("date"));
        } catch (ParseException e) {
            playAt = null;
        }

        long _mods = object.isNull("enabled_mods") ? 0 : Long.parseLong(object.getString("enabled_mods"));
        mods = Mod.parse(_mods);

        counts = new Count();
    }

    public Beatmap getBeatmap() {
        //TODO return beatmap
        return null;
    }

    public User getUser() throws NotFoundException, InvalidTokenException, IOException {
        return OsuClient.client.getUser(userId, true);
    }

    private class Count {
        long _300, _100, _50, miss, maxCombo;

        public Count() {
            _300 = object.isNull("count300") ? 0 : Long.parseLong(object.getString("count300"));
            _100 = object.isNull("count100") ? 0 : Long.parseLong(object.getString("count100"));
            _50 = object.isNull("count50") ? 0 : Long.parseLong(object.getString("count50"));
            miss = object.isNull("countmiss") ? 0 : Long.parseLong(object.getString("countmiss"));
            maxCombo = object.isNull("maxcombo") ? 0 : Long.parseLong(object.getString("maxcombo"));
        }
    }

    public enum Mod {
        NoFail(1),
        Easy(2),
        TouchDevice(4),
        Hidden(8),
        HardRock(16),
        SuddenDeath(32),
        DoubleTime(64),
        Relax(128),
        HalfTime(256),
        Nightcore(512),
        Flashlight(1024),
        Autoplay(2048),
        SpunOut(4096),
        AutoPilot(8192),
        Perfect(16384),
        Key4(32768),
        Key5(65536),
        Key6(131072),
        Key7(262144),
        Key8(524288),
        FadeIn(1048576),
        Random(2097152),
        Cinema(4194304),
        Target(8388608),
        Key9(16777216),
        KeyCoop(33554432),
        Key1(67108864),
        Key3(134217728),
        Key2(268435456),
        ScoreV2(536870912),
        Mirror(1073741824);

        public final long bit;

        Mod(int bit) {
            this.bit = bit;
        }

        public static Mod[] parse(long bit) {
            List<Mod> values = Arrays.stream(values())
                    .sorted(Comparator.comparingLong(Mod::getBit))
                    .sorted(Comparator.reverseOrder())
                    .collect(Collectors.toList());
            List<Mod> mods = new ArrayList<>();
            while(bit > 0) for(Mod mod : values) {
                if(mod.bit <= bit) {
                    mods.add(mod);
                    bit -= mod.bit;
                }
            }

            return mods.toArray(new Mod[mods.size()]);
        }

        public long getBit() {
            return bit;
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
