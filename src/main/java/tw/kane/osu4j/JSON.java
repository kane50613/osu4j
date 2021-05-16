package tw.kane.osu4j;

import com.jayway.jsonpath.DocumentContext;
import org.json.JSONObject;

public class JSON {
//    public static boolean checkExist(JSONObject object, String path) {
//        if(object.isNull(path.split("\\.")[0]))
//            return false;
//        if(path.split("\\.").length > 1)
//            return checkExist(
//                    object.getJSONObject(path.split("\\.")[0]),
//                    path.substring(path.split("\\.")[0].length() + 1)
//            );
//        return true;
//    }
    public static <T> T get(DocumentContext json, String path, Class<T> _class, T whenNull) {
        T obj;
        try {
            obj = json.read(path, _class);
        } catch (Exception e) {
            obj = whenNull;
        }
        return obj;
    }
}
