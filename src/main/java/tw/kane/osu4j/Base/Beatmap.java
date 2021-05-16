package tw.kane.osu4j.Base;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.json.JSONObject;

public class Beatmap {
    private final DocumentContext object;

    public Beatmap(String json) {
        object = JsonPath.parse(json);
    }

    public Beatmap(JSONObject json) {
        object = JsonPath.parse(json.toString());
    }
}
