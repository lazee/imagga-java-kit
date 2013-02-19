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
import net.jakobnielsen.imagga.crop_slice.bean.Cropping;
import net.jakobnielsen.imagga.crop_slice.bean.Region;
import net.jakobnielsen.imagga.crop_slice.bean.Resolution;
import net.jakobnielsen.imagga.crop_slice.bean.SmartCropping;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.ArrayList;
import java.util.List;

import static net.jakobnielsen.imagga.convert.ConverterTools.getInteger;
import static net.jakobnielsen.imagga.convert.ConverterTools.getString;

public class SmartCroppingConverter implements Converter<List<SmartCropping>> {

    private static final String SMART_CROPPINGS = "smart_croppings";

    private static final String CROPPINGS = "croppings";

    @Override
    public List<SmartCropping> convert(String jsonString) {
        if (jsonString == null) {
            throw new ConverterException("The given JSON string is null");
        }

        JSONObject json = (JSONObject) JSONValue.parse(jsonString);
        if (!json.containsKey(SMART_CROPPINGS)) {
            throw new ConverterException(SMART_CROPPINGS + " key missing from json : " + jsonString);
        }
        JSONArray jsonArray = (JSONArray) json.get(SMART_CROPPINGS);

        List<SmartCropping> smartCroppings = new ArrayList<SmartCropping>();

        for (Object aJsonArray : jsonArray) {

            JSONObject smartCroppingObject = (JSONObject) aJsonArray;

            String url = getString("url", smartCroppingObject);

            List<Cropping> croppings = new ArrayList<Cropping>();

            if (smartCroppingObject.containsKey(CROPPINGS)) {

                JSONArray croppingsArray = (JSONArray) smartCroppingObject.get(CROPPINGS);

                for (Object aCroppingsArray : croppingsArray) {

                    JSONObject croppingObject = (JSONObject) aCroppingsArray;

                    croppings.add(new Cropping(
                            new Resolution(
                                    getInteger("target_width", croppingObject),
                                    getInteger("target_height", croppingObject)),
                            new Region(
                                    getInteger("x1", croppingObject),
                                    getInteger("y1", croppingObject),
                                    getInteger("x2", croppingObject),
                                    getInteger("y2", croppingObject)
                            )));
                }
                smartCroppings.add(new SmartCropping(url, croppings));
            }
        }

        return smartCroppings;
    }


}
