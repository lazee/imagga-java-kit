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

package net.jakobnielsen.imagga.color.client;

import net.jakobnielsen.imagga.client.APIClient;
import net.jakobnielsen.imagga.client.APIClientConfig;
import net.jakobnielsen.imagga.client.ApiConstants;
import net.jakobnielsen.imagga.client.Method;
import net.jakobnielsen.imagga.color.bean.Color;
import net.jakobnielsen.imagga.color.bean.ColorResult;
import net.jakobnielsen.imagga.color.bean.IndexableImage;
import net.jakobnielsen.imagga.color.bean.RankSimilarity;
import net.jakobnielsen.imagga.color.convert.ColorsConverter;
import net.jakobnielsen.imagga.color.convert.SimilarColorsConverter;

import java.util.ArrayList;
import java.util.List;

import static net.jakobnielsen.imagga.ListTools.LIST_SEPARATOR;

public class ColorAPIClient extends APIClient {

    protected ColorAPIClient(APIClientConfig apiConfig) {
        super(apiConfig, "colorsearchserver");
    }

    protected String colorsByUrlsAsJson(ColorsByUrlsRequest request) {
        Method method = new Method("imagga.colorsearch.extract");

        List<String> urls = new ArrayList<String>();
        List<String> ids = new ArrayList<String>();
        for (IndexableImage iu : request.getUrlsToProcess()) {
            urls.add(iu.getUrl());
            if (iu.getId() != null) {
                ids.add(iu.getId().toString());
            }
        }
        method.addParam(ApiConstants.URLS, urls);
        if (ids != null && ids.size() > 0) {
            method.addParam("ids", ids);
        }
        method.addParam("extract_overall_colors", request.isExtractOverallColors() ? TRUE_VALUE : FALSE_VALUE);
        method.addParam("extract_object_colors", request.isExtractObjectColors() ? TRUE_VALUE : FALSE_VALUE);
        return callMethod(method);
    }

    public List<ColorResult> colorsByUrls(ColorsByUrlsRequest request) {
        ColorsConverter converter = new ColorsConverter();
        return converter.convert(colorsByUrlsAsJson(request));
    }

    protected String rankSimilarColorAsJson(RankSimilarColorRequest request) {
        Method method = new Method("imagga.colorsearch.rank");

        List<String> colorVector = new ArrayList<String>();
        for (Color c : request.getColorVector()) {
            colorVector.add(
                    c.getPercent().toString() +
                            LIST_SEPARATOR + c.getR() +
                            LIST_SEPARATOR + c.getG() +
                            LIST_SEPARATOR + c.getB() +
                            LIST_SEPARATOR);
        }
        method.addParam("color_vector", colorVector);
        method.addParam("type", request.getColorIndexType().getName());
        method.addParam("dist", request.getDist().toString());
        method.addParam("count", request.getCount().toString());

        return callMethod(method);
    }

    public List<RankSimilarity> rankSimilarColor(RankSimilarColorRequest request) {
        SimilarColorsConverter converter = new SimilarColorsConverter();
        return converter.convert(rankSimilarColorAsJson(request));
    }

}
