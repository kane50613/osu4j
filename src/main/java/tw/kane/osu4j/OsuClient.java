package tw.kane.osu4j;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import tw.kane.osu4j.Base.Score;
import tw.kane.osu4j.Base.User;
import tw.kane.osu4j.Exception.InvalidTokenException;
import tw.kane.osu4j.Exception.NotFoundException;

import java.io.IOException;
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
     * @return User
     * @throws InvalidTokenException if token provided to OsuClient wrong
     * @throws NotFoundException if user not found
     */
    public User getUser(String id, Mode mode, boolean allowCached) throws IOException, InvalidTokenException, NotFoundException {
        if(allowCached && Cache.userCache.containsKey(id))
            return Cache.userCache.get(id);

        Request request = new Request.Builder()
                .url(String.format("https://osu.ppy.sh/api/v2/users/%s/%s",
                        id,
                        mode.getName()
                ))
                .headers(oauth.header())
                .build();
        Response response = httpClient.newCall(request).execute();
        String responseString = response.body().string();
        if(responseString.startsWith("{")) {
            JSONObject result = new JSONObject(responseString);
            if(!result.isNull("authentication"))
                throw new InvalidTokenException("token wrong");
        }
        else {
            JSONArray userArray = new JSONArray(responseString);
            if(userArray.length() == 0)
                throw new NotFoundException();
            User user = new User(userArray.getJSONObject(0).toString());
            Cache.userCache.put(id, user);
            return user;
        }
        return null;
    }

    /**
     * get user's recent plays in 24hrs
     * @param id user's name or id
     * @return User
     * @throws InvalidTokenException if token provided to OsuClient wrong
     * @throws NotFoundException if user not found
     */
    public Score[] getUserScores(String id, ScoreType type, boolean includeFails, Mode mode) throws IOException, InvalidTokenException, NotFoundException {
        Request request = new Request.Builder()
                .url(String.format("https://osu.ppy.sh/api/v2/users/%s/scores/%s?include_fails=%c&mode=%s",
                        id,
                        type.getName(),
                        includeFails ? 1 : 0,
                        mode.getName()
                ))
                .build();
        Response response = httpClient.newCall(request).execute();
        String responseString = response.body().string();
        if(responseString.startsWith("{")) {
            JSONObject result = new JSONObject(responseString);
            if(!result.isNull("authentication"))
                throw new InvalidTokenException(result.getString("token wrong"));
        }
        else {
            JSONArray resultArray = new JSONArray(responseString);
            if(resultArray.length() == 0)
                throw new NotFoundException();
            List<Score> scores = new ArrayList<>();
            for (int i = 0; i < resultArray.length(); i++) {
                scores.add(new Score(resultArray.getJSONObject(i)));
            }
            return scores.toArray(scores.toArray(new Score[scores.size()]));
        }
        return null;
    }
}
