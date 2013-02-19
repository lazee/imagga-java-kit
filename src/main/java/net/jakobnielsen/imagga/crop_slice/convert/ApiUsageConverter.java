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

package net.jakobnielsen.imagga.crop_slice.convert;

import net.jakobnielsen.imagga.convert.Converter;
import net.jakobnielsen.imagga.convert.ConverterException;
import net.jakobnielsen.imagga.crop_slice.bean.ApiUsage;
import net.jakobnielsen.imagga.crop_slice.bean.Usage;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static net.jakobnielsen.imagga.convert.ConverterTools.getDouble;
import static net.jakobnielsen.imagga.convert.ConverterTools.getLong;

public class ApiUsageConverter implements Converter<ApiUsage> {

    public static final String API_USAGE = "api_usage";

    public static final String UNIX = "UNIX";

    public static final String COUNT = "count";

    @Override
    public ApiUsage convert(String jsonString) {
        if (jsonString == null) {
            throw new ConverterException("The given JSON string is null");
        }

        JSONObject json = (JSONObject) JSONValue.parse(jsonString);

        if (!json.containsKey(API_USAGE)) {
            throw new ConverterException(API_USAGE + " key missing from json : " + jsonString);
        }
        json = (JSONObject) json.get(API_USAGE);

        return doConvert(json);
    }

    private ApiUsage doConvert(JSONObject json) {
        Map<String, Usage> usageMap = new HashMap<String, Usage>();
        Date startTime = null;
        Date endTime = null;
        Double totalPayable = null;

        for (Object okey : json.keySet().toArray()) {
            String key = (String) okey;
            if ("start_time".equals(key)) {
                JSONObject value = (JSONObject) json.get(key);
                if (value.containsKey(UNIX)) {
                    startTime = new Date(getLong(UNIX, value));
                }
            } else if ("end_time".equals(key)) {
                JSONObject value = (JSONObject) json.get(key);
                if (value.containsKey(UNIX)) {
                    endTime = new Date(getLong(UNIX, value));
                }
            } else if ("total_payable".equals(key)) {
                totalPayable = getDouble(key, json);
            } else {
                JSONObject value = (JSONObject) json.get(key);
                if (value.containsKey(COUNT)) {
                    usageMap.put(key, new Usage(getLong(COUNT, value), getDouble("total_price", value)));
                }
            }
        }

        return new ApiUsage(startTime, endTime, usageMap, totalPayable);
    }
}
