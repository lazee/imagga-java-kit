package net.jakobnielsen.imagga.crop_slice.convert;

import net.jakobnielsen.imagga.ConverterException;
import net.jakobnielsen.imagga.crop_slice.bean.ApiUsage;
import net.jakobnielsen.imagga.crop_slice.bean.Usage;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
                    startTime = new Date((Long) value.get(UNIX));
                }
            } else if ("end_time".equals(key)) {
                JSONObject value = (JSONObject) json.get(key);
                if (value.containsKey(UNIX)) {
                    endTime = new Date((Long) value.get(UNIX));
                }
            } else if ("total_payable".equals(key)) {
                totalPayable = (Double) json.get(key);
            } else {
                JSONObject value = (JSONObject) json.get(key);
                if (value.containsKey(COUNT)) {
                    usageMap.put(key, new Usage(Long.valueOf((String) value.get(COUNT)), Double.parseDouble(
                            (String) value.get("total_price"))));
                }
            }
        }

        return new ApiUsage(startTime, endTime, usageMap, totalPayable);
    }
}
