package tw.kane.osu4j;

import tw.kane.osu4j.Base.Beatmap;
import tw.kane.osu4j.Base.User;

import java.util.HashMap;

public class Cache {
    public static HashMap<String, User> userCache = new HashMap<>();
    public static HashMap<String, Beatmap> beatmapCache = new HashMap<>();
}
