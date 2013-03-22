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

package net.jakobnielsen.imagga.upload.convert;

import net.jakobnielsen.imagga.convert.Converter;
import net.jakobnielsen.imagga.convert.ConverterException;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class UploadConverter implements Converter<String> {

    public static final String UPLOAD_FOR_PROCESSING = "upload_for_processing";

    @Override
    public String convert(String jsonString) {
        if (jsonString == null) {
            throw new ConverterException("The given JSON string is null");
        }

        JSONObject json = (JSONObject) JSONValue.parse(jsonString);
        if (!json.containsKey(UPLOAD_FOR_PROCESSING)) {
            throw new ConverterException(UPLOAD_FOR_PROCESSING + " key missing from json : " + jsonString);
        }
        JSONObject json2 = (JSONObject) json.get(UPLOAD_FOR_PROCESSING);

        return json2.get("upload_code").toString();
    }
}
