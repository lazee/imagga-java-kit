/*
 * Copyright 2013 Jakob Vad Nielsen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
