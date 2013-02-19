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

package net.jakobnielsen.imagga.color.convert;

import net.jakobnielsen.imagga.color.bean.ColorResult;
import net.jakobnielsen.imagga.color.bean.ExtendedColor;
import net.jakobnielsen.imagga.color.bean.Info;
import net.jakobnielsen.imagga.convert.Converter;
import net.jakobnielsen.imagga.convert.ConverterException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.ArrayList;
import java.util.List;

import static net.jakobnielsen.imagga.convert.ConverterTools.getDouble;
import static net.jakobnielsen.imagga.convert.ConverterTools.getLong;
import static net.jakobnielsen.imagga.convert.ConverterTools.getString;

public class ColorsConverter implements Converter<List<ColorResult>> {

    public static final String COLORS = "colors";

    public static final String OBJECT_PERCENTAGE = "object_percentage";

    @Override
    public List<ColorResult> convert(String jsonString) {
        if (jsonString == null) {
            throw new ConverterException("The given JSON string is null");
        }

        JSONObject json = (JSONObject) JSONValue.parse(jsonString);

        if (!json.containsKey(COLORS)) {
            throw new ConverterException(COLORS + " key missing from json : " + jsonString);
        }

        JSONArray jsonArray = (JSONArray) json.get(COLORS);
        List<ColorResult> colorResults = new ArrayList<ColorResult>();

        for (Object co : jsonArray) {

            JSONObject colorResultJSON = (JSONObject) co;
            String url = getString("url", colorResultJSON);
            JSONObject infoJSON = (JSONObject) colorResultJSON.get("info");
            List<ExtendedColor> imageColors = new ArrayList<ExtendedColor>();
            List<ExtendedColor> foregroundColors = new ArrayList<ExtendedColor>();
            List<ExtendedColor> backgroundColors = new ArrayList<ExtendedColor>();
            Double objectPercentage = null;
            Long colorVariance = null;

            addColors("image_colors", infoJSON, imageColors);
            addColors("foreground_colors", infoJSON, foregroundColors);
            addColors("background_colors", infoJSON, backgroundColors);

            if (infoJSON.containsKey(OBJECT_PERCENTAGE)) {
                objectPercentage = getDouble(OBJECT_PERCENTAGE, infoJSON);
            }

            if (infoJSON.containsKey("color_variance")) {
                colorVariance = getLong("color_variance", infoJSON);
            }

            colorResults.add(new ColorResult(url,
                    new Info(imageColors, foregroundColors, backgroundColors, objectPercentage, colorVariance)));
        }

        return colorResults;
    }

    private void addColors(String key, JSONObject infoJSON, List<ExtendedColor> imageColors) {
        if (infoJSON.containsKey(key)) {

            Object colorsObject = infoJSON.get(key);

            if (colorsObject instanceof JSONArray) {
                JSONArray colorsArrays = (JSONArray) colorsObject;
                for (Object ac : colorsArrays) {
                    imageColors.add(createColor((JSONObject) ac));
                }
            }

        }
    }

    private ExtendedColor createColor(JSONObject colorJSON) {
        return new ExtendedColor(
                getDouble("percent", colorJSON),
                getLong("r", colorJSON),
                getLong("g", colorJSON),
                getLong("b", colorJSON),
                getString("html_code", colorJSON),
                getString("closest_palette_color", colorJSON),
                getString("closest_palette_color_parent", colorJSON),
                getDouble("closest_palette_distance", colorJSON)
        );
    }
}
