package net.jakobnielsen.imagga.color.convert;

import net.jakobnielsen.imagga.color.bean.Color;
import net.jakobnielsen.imagga.color.bean.ColorResult;
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
            List<Color> imageColors = new ArrayList<Color>();
            List<Color> foregroundColors = new ArrayList<Color>();
            List<Color> backgroundColors = new ArrayList<Color>();
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

    private void addColors(String key, JSONObject infoJSON, List<Color> imageColors) {
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

    private Color createColor(JSONObject colorJSON) {
        return new Color(
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
