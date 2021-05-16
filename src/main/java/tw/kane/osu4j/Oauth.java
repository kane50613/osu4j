package tw.kane.osu4j;

import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Oauth {
    public static Oauth instance;

    private String clientId, secret, token;

    public Oauth(String clientId, String secret) {
        if(instance != null)
            return;

        instance = this;
        this.clientId = clientId;
        this.secret = secret;

        try {
            grant();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(
                new Tasker(),
                86300000L,
                86300000L
        );
    }

    public Headers header() {
        return new Headers.Builder().add(
                "Authorization",
                "Bearer " + getToken()
        ).build();
    }

    public void grant() throws IOException {
        Request request = new Request.Builder()
                .url("https://osu.ppy.sh/oauth/token")
                .method("POST", RequestBody.create(
                        MediaType.parse("application/json; charset=utf-8"),
                        String.format(
                                "{\"client_id\":%s,\"scope\":\"public\",\"client_secret\":\"%s\",\"grant_type\": \"client_credentials\"}",
                                clientId,
                                secret
                        )
                ))
                .build();
        Response response = OsuClient.httpClient.newCall(request).execute();
        String responseString = response.body().string();
        JSONObject responseJson = new JSONObject(responseString);
        if(!responseJson.isNull("access_token"))
            token = responseJson.getString("access_token");
    }

    public String getToken() {
        return token;
    }

    private class Tasker extends TimerTask {
        @Override
        public void run() {
            try {
                grant();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
