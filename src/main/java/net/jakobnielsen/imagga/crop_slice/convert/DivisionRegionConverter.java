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
import net.jakobnielsen.imagga.crop_slice.bean.DivisionRegion;
import net.jakobnielsen.imagga.crop_slice.bean.Region;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.ArrayList;
import java.util.List;

import static net.jakobnielsen.imagga.convert.ConverterTools.getInteger;
import static net.jakobnielsen.imagga.convert.ConverterTools.getString;

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

            String url = getString("url", divisionRegionObject);

            List<Region> regions = new ArrayList<Region>();

            if (divisionRegionObject.containsKey(REGIONS)) {

                if (divisionRegionObject.get(REGIONS) != null &&
                        divisionRegionObject.get(REGIONS) instanceof JSONArray) {
                    JSONArray regionsArrays = (JSONArray) divisionRegionObject.get(REGIONS);

                    for (Object aRegionsArray : regionsArrays) {

                        JSONObject regionObject = (JSONObject) aRegionsArray;

                        regions.add(new Region(
                                getInteger("x1", regionObject),
                                getInteger("y1", regionObject),
                                getInteger("x2", regionObject),
                                getInteger("y2", regionObject)
                        ));
                    }
                    divisionRegions.add(new DivisionRegion(url, regions));
                }
            }
        }

        return divisionRegions;
    }
}
