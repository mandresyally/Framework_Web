package main.java.util;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Map;

public final class JsonUtil {
    private JsonUtil() {
    }

    public static String toJson(Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof String || value instanceof Character) {
            return quote(value.toString());
        }
        if (value instanceof Number || value instanceof Boolean) {
            return value.toString();
        }
        if (value instanceof Map<?, ?> map) {
            StringBuilder json = new StringBuilder("{");
            Iterator<? extends Map.Entry<?, ?>> entries = map.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<?, ?> entry = entries.next();
                json.append(quote(String.valueOf(entry.getKey())))
                        .append(':')
                        .append(toJson(entry.getValue()));
                if (entries.hasNext()) {
                    json.append(',');
                }
            }
            return json.append('}').toString();
        }
        if (value instanceof Iterable<?> iterable) {
            StringBuilder json = new StringBuilder("[");
            Iterator<?> values = iterable.iterator();
            while (values.hasNext()) {
                json.append(toJson(values.next()));
                if (values.hasNext()) {
                    json.append(',');
                }
            }
            return json.append(']').toString();
        }
        if (value.getClass().isArray()) {
            StringBuilder json = new StringBuilder("[");
            for (int index = 0; index < Array.getLength(value); index++) {
                if (index > 0) {
                    json.append(',');
                }
                json.append(toJson(Array.get(value, index)));
            }
            return json.append(']').toString();
        }
        return quote(value.toString());
    }

    private static String quote(String value) {
        StringBuilder json = new StringBuilder("\"");
        for (int index = 0; index < value.length(); index++) {
            char character = value.charAt(index);
            switch (character) {
                case '"' -> json.append("\\\"");
                case '\\' -> json.append("\\\\");
                case '\b' -> json.append("\\b");
                case '\f' -> json.append("\\f");
                case '\n' -> json.append("\\n");
                case '\r' -> json.append("\\r");
                case '\t' -> json.append("\\t");
                default -> json.append(character);
            }
        }
        return json.append('"').toString();
    }
}