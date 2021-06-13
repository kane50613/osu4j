package tw.kane.osu4j;

import com.jayway.jsonpath.DocumentContext;

public class JSON {
    public static <T> T get(DocumentContext json, String path, Class<T> _class, T whenNull) {
        T obj;
        try {
            obj = json.read(path, _class);
            if(obj == null) obj = whenNull;
        } catch (Exception e) {
            obj = whenNull;
        }
        return obj;
    }
}
