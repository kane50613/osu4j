package tw.kane.osu4j;

import com.sun.org.apache.bcel.internal.generic.PUSH;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

import static tw.kane.osu4j.OsuClient.httpClient;
import static tw.kane.osu4j.OsuClient.oauth;

public class API {

    public static String ENDPOINT = "https://osu.ppy.sh/api/v2";

    public static String makeRequest(String url, Object ...args) throws IOException {
        Request request = new Request.Builder()
                .url(String.format(ENDPOINT + url, args))
                .headers(oauth.header())
                .build();
        Response response = httpClient.newCall(request).execute();
        return response.body().string();
    }
}
