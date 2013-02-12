package net.jakobnielsen.imagga.crop_slice.convert;

import net.jakobnielsen.imagga.ConverterException;
import net.jakobnielsen.imagga.crop_slice.bean.DivisionRegion;
import net.jakobnielsen.imagga.crop_slice.bean.Region;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.ArrayList;
import java.util.List;

public class DivisionRegionConverter implements Converter<List<DivisionRegion>> {

    public static final String DIVISION_REGIONS = "division_regions";

    public static final String REGIONS = "regions";

    @Override
    public List<DivisionRegion> convert(String jsonString) {
        if (jsonString == null) {
            throw new ConverterException("The given JSON string is null");
        }

        JSONObject json = (JSONObject) JSONValue.parse(jsonString);
        if (!json.containsKey(DIVISION_REGIONS)) {
            throw new ConverterException(DIVISION_REGIONS + " key missing from json : " + jsonString);
        }
        JSONArray jsonArray = (JSONArray) json.get(DIVISION_REGIONS);
        List<DivisionRegion> divisionRegions = new ArrayList<DivisionRegion>();

        for (Object aJsonArray : jsonArray) {

            JSONObject divisionRegionObject = (JSONObject) aJsonArray;

            String url = (String) divisionRegionObject.get("url");

            List<Region> regions = new ArrayList<Region>();

            if (divisionRegionObject.containsKey(REGIONS)) {

                JSONArray regionsArrays = (JSONArray) divisionRegionObject.get(REGIONS);

                for (Object aRegionsArray : regionsArrays) {

                    JSONObject regionObject = (JSONObject) aRegionsArray;

                    regions.add(new Region(
                            Long.valueOf((String) regionObject.get("x1")),
                            Long.valueOf((String) regionObject.get("y1")),
                            Long.valueOf((String) regionObject.get("x2")),
                            Long.valueOf((String) regionObject.get("y2"))
                    ));
                }
                divisionRegions.add(new DivisionRegion(url, regions));
            }
        }

        return divisionRegions;
    }
}
