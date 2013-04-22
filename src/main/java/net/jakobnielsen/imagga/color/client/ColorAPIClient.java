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

    public static final String EXTRACT_OVERALL_COLORS = "extract_overall_colors";

    public static final String EXTRACT_OBJECT_COLORS = "extract_object_colors";

    public static final String IMAGGA_COLORSEARCH_EXTRACT = "imagga.colorsearch.extract";

    public static final String IMAGGA_COLORSEARCH_RANK = "imagga.colorsearch.rank";

    protected ColorAPIClient(APIClientConfig apiConfig) {
        super(apiConfig, "colorsearchserver");
    }

    protected String colorsByUrlsAsJson(ColorsByUrlsRequest request) {
        Method method = new Method(IMAGGA_COLORSEARCH_EXTRACT);

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
        method.addParam(EXTRACT_OVERALL_COLORS, request.isExtractOverallColors() ? TRUE_VALUE : FALSE_VALUE);
        method.addParam(EXTRACT_OBJECT_COLORS, request.isExtractObjectColors() ? TRUE_VALUE : FALSE_VALUE);
        return callMethod(method);
    }

    public List<ColorResult> colorsByUrls(ColorsByUrlsRequest request) {
        ColorsConverter converter = new ColorsConverter();
        return converter.convert(colorsByUrlsAsJson(request));
    }

    protected String colorsByUploadCodeAsJson(ColorsByUploadCodeRequest request) {
        Method method = new Method(IMAGGA_COLORSEARCH_EXTRACT);

        method.addParam(ApiConstants.UPLOAD_CODE, request.getUploadCode());
        method.addParam(ApiConstants.DELETE_AFTERWARDS, request.isDeleteAfterwords() ? TRUE_VALUE : FALSE_VALUE);
        if (request.getImageId() != null) {
            List<String> ids = new ArrayList<String>();
            ids.add(request.getImageId().toString());
        }
        method.addParam(EXTRACT_OVERALL_COLORS, request.isExtractOverallColors() ? TRUE_VALUE : FALSE_VALUE);
        method.addParam(EXTRACT_OBJECT_COLORS, request.isExtractObjectColors() ? TRUE_VALUE : FALSE_VALUE);
        return callMethod(method);
    }

    public List<ColorResult> colorsByUploadCode(ColorsByUploadCodeRequest request) {
        ColorsConverter converter = new ColorsConverter();
        return converter.convert(colorsByUploadCodeAsJson(request));
    }

    protected String rankSimilarColorAsJson(RankSimilarColorRequest request) {
        Method method = new Method(IMAGGA_COLORSEARCH_RANK);

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
