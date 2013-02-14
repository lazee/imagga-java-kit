package net.jakobnielsen.imagga.convert;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ConverterTools {

    private ConverterTools() {
        // Intentional
    }

    public static Integer getInteger(String key, Object o) {
        return Integer.valueOf(getString(key, o));
    }

    public static Long getLong(String key, Object o) {
        return Long.valueOf(getString(key, o));
    }

    public static Double getDouble(String key, Object o) {
        return Double.valueOf(getString(key, o));
    }

    public static String getString(String key, Object o) {
        if (key == null || o == null) {
            return null;
        } else if (o instanceof JSONArray) {
            return null; // Maybe throw exception in the future.
        } else if (o instanceof JSONObject) {
            JSONObject jo = (JSONObject) o;
            if (jo.containsKey(key)) {
                return jo.get(key) == null ? null : jo.get(key).toString();
            }
        }
        return null;

    }

}
