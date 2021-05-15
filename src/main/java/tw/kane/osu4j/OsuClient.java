package tw.kane.osu4j;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import tw.kane.osu4j.Exception.InvalidTokenException;
import tw.kane.osu4j.Exception.NotFoundException;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class OsuClient {
    private final String token;
    private final OkHttpClient httpClient = new OkHttpClient();

    public OsuClient(String token) {
        this.token = token;
    }

    /**
     * get user info
     * @param id user's name or id
     * @return User
     * @throws InvalidTokenException if token provided to OsuClient wrong
     * @throws NotFoundException if user not found
     */
    public User getUser(String id) throws IOException, InvalidTokenException, NotFoundException {
        Request request = new Request.Builder()
                .url(String.format("https://osu.ppy.sh/api/get_user?k=%s&u=%s",
                        token,
                        id
                ))
                .build();
        Response response = httpClient.newCall(request).execute();
        String responseString = response.body().string();
        if(responseString.startsWith("{")) {
            JSONObject result = new JSONObject(responseString);
            if(!result.isNull("error"))
                throw new InvalidTokenException(result.getString("error"));
        }
        else {
            JSONArray user = new JSONArray(responseString);
            if(user.length() == 0)
                throw new NotFoundException();
            return new User((JSONObject) user.get(0));
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
    public Score[] getUserRecent(String id) throws IOException, InvalidTokenException, NotFoundException {
        Request request = new Request.Builder()
                .url(String.format("https://osu.ppy.sh/api/get_user_recent?k=%s&u=%s",
                        token,
                        id
                ))
                .build();
        Response response = httpClient.newCall(request).execute();
        String responseString = response.body().string();
        if(responseString.startsWith("{")) {
            JSONObject result = new JSONObject(responseString);
            if(!result.isNull("error"))
                throw new InvalidTokenException(result.getString("error"));
        }
        else {
            JSONArray resultArray = new JSONArray(responseString);
            if(resultArray.length() == 0)
                throw new NotFoundException();
            List<Score> scores = new LinkedList<>();
            resultArray.toList().forEach(score ->
                    scores.add(new Score((JSONObject) score))
            );
            return scores.toArray(scores.toArray(new Score[scores.size()]));
        }
        return null;
    }

    /**
     * get user's best plays
     * @param id user's name or id
     * @return Score[]
     * @throws InvalidTokenException if token provided to OsuClient wrong
     * @throws NotFoundException if user not found
     */
    public Score[] getUserBest(String id) throws IOException, InvalidTokenException, NotFoundException {
        Request request = new Request.Builder()
                .url(String.format("https://osu.ppy.sh/api/get_user_best?k=%s&u=%s",
                        token,
                        id
                ))
                .build();
        Response response = httpClient.newCall(request).execute();
        String responseString = response.body().string();
        if(responseString.startsWith("{")) {
            JSONObject result = new JSONObject(responseString);
            if(!result.isNull("error"))
                throw new InvalidTokenException(result.getString("error"));
        }
        else {
            JSONArray resultArray = new JSONArray(responseString);
            if(resultArray.length() == 0)
                throw new NotFoundException();
            List<Score> scores = new LinkedList<>();
            resultArray.toList().forEach(score ->
                    scores.add(new Score((JSONObject) score))
            );
            return scores.toArray(scores.toArray(new Score[scores.size()]));
        }
        return null;
    }
}
