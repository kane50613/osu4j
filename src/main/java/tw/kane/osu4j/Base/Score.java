package tw.kane.osu4j.Base;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.json.JSONObject;

import java.util.*;
import java.util.stream.Collectors;

public class Score {
    private final DocumentContext object;

    public String mapId, userId, scoreId;
    public long score;
    public boolean isPerfect;
    public Mod[] mods;
    public Rank rank;
    public Date playAt;

    public Count counts;

    public Score(JSONObject json) {
        this.object = JsonPath.parse(json.toString());

        counts = new Count();
    }
    public class Count {
        public long _300, _100, _50, miss, maxCombo;

        public Count() {

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
