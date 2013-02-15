package net.jakobnielsen.imagga.color.convert;

import net.jakobnielsen.imagga.color.bean.RankSimilarity;
import net.jakobnielsen.imagga.convert.Converter;
import net.jakobnielsen.imagga.convert.ConverterException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.ArrayList;
import java.util.List;

import static net.jakobnielsen.imagga.convert.ConverterTools.getDouble;
import static net.jakobnielsen.imagga.convert.ConverterTools.getLong;

public class SimilarColorsConverter implements Converter<List<RankSimilarity>> {

    public static final String RANK_SIMILARITY = "rank_similarity";

    @Override
    public List<RankSimilarity> convert(String jsonString) {
        if (jsonString == null) {
            throw new ConverterException("The given JSON string is null");
        }

        JSONObject json = (JSONObject) JSONValue.parse(jsonString);

        if (!json.containsKey(RANK_SIMILARITY)) {
            throw new ConverterException(RANK_SIMILARITY + " key missing from json : " + jsonString);
        }

        JSONArray jsonArray = (JSONArray) json.get(RANK_SIMILARITY);
        List<RankSimilarity> rankResults = new ArrayList<RankSimilarity>();

        for (Object co : jsonArray) {
            JSONObject o = (JSONObject) co;
            rankResults.add(new RankSimilarity(getLong("id", o), getDouble("dist", o)));
        }

        return rankResults;
    }
}
