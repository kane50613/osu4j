package tw.kane.osu4j;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import tw.kane.osu4j.Base.Beatmap;
import tw.kane.osu4j.Base.Score;
import tw.kane.osu4j.Base.User;
import tw.kane.osu4j.Exception.InvalidTokenException;
import tw.kane.osu4j.Exception.NotFoundException;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class OsuClient {
    public static final OkHttpClient httpClient = new OkHttpClient();

    public static OsuClient client;
    public static Oauth oauth;

    public OsuClient(String clientId, String secret) {
        oauth = new Oauth(clientId, secret);
        client = this;
    }

    /**
     * get user info
     * @param id user's name or id
     * @param mode get user specify mode's info
     * @param allowCached allow cached for faster response
     * @return User
     * @throws InvalidTokenException if token provided to OsuClient wrong
     * @throws NotFoundException if user not found
     */
    public User getUser(String id, Mode mode, boolean allowCached) throws IOException, InvalidTokenException, NotFoundException {
        if(allowCached && Cache.userCache.containsKey(id))
            return Cache.userCache.get(id);

        String response = API.makeRequest(
                "/users/%s/%s",
                URLEncoder.encode(id, "UTF-8"),
                mode.getName());
        JSONObject result = new JSONObject(response);
        if(!result.isNull("authentication"))
            throw new InvalidTokenException("token wrong");
        if(!result.isNull("error"))
            throw new NotFoundException();
        User user = new User(result);
        Cache.userCache.put(id, user);
        return user;
    }

    /**
     * get user's recent plays in 24hrs
     * @param id user's ID ONLY
     * @param type score type best or recent
     * @param includeFails whether include failed scores
     * @param mode get specify mode's score
     * @return User
     * @throws InvalidTokenException if token provided to OsuClient wrong
     * @throws NotFoundException if user not found
     */
    public Score[] getUserScores(String id, ScoreType type, boolean includeFails, Mode mode) throws IOException, InvalidTokenException, NotFoundException {
        String response = API.makeRequest("/users/%s/scores/%s?include_fails=%s&mode=%s",
                URLEncoder.encode(id, "UTF-8"),
                type.getName(),
                includeFails ? "1" : "0",
                mode.getName());
        if(response.startsWith("{")) {
            JSONObject result = new JSONObject(response);
            if(!result.isNull("authentication"))
                throw new InvalidTokenException("token wrong");
        }
        else {
            JSONArray resultArray = new JSONArray(response);
            if(resultArray.length() == 0)
                throw new NotFoundException();
            List<Score> scores = new ArrayList<>();
            for (int i = 0; i < resultArray.length(); i++) {
                scores.add(new Score(resultArray.getJSONObject(i)));
            }
            return scores.toArray(new Score[0]);
        }
        throw new NotFoundException();
    }

    public Beatmap getBeatmap(String id) throws IOException, InvalidTokenException, NotFoundException {
        String response = API.makeRequest("/beatmaps/%s", URLEncoder.encode(id, "UTF-8"));
        JSONObject result = new JSONObject(response);
        if(!result.isNull("authentication"))
            throw new InvalidTokenException("token wrong");
        if(!result.isNull("error"))
            throw new NotFoundException();
        return new Beatmap(result);
    }
}
