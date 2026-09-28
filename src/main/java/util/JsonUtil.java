package main.java.util;

import com.google.gson.Gson;

public final class JsonUtil {
    private static final Gson GSON = new Gson();

    private JsonUtil() {
    }

    public static String serializeApiResult(Object value) {
        if (value instanceof String stringValue) {
            return stringValue;
        }
        return GSON.toJson(value);
    }
}