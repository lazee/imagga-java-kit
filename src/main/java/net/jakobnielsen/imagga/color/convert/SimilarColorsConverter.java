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
